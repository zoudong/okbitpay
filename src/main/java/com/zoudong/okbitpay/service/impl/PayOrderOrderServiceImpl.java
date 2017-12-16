package com.zoudong.okbitpay.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zoudong.okbitpay.config.Config;
import com.zoudong.okbitpay.dao.PayOrderMapper;
import com.zoudong.okbitpay.model.PayOrder;
import com.zoudong.okbitpay.po.PayStatus;
import com.zoudong.okbitpay.po.Status;
import com.zoudong.okbitpay.service.PayOrderService;
import com.zoudong.okbitpay.util.http.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PayOrderOrderServiceImpl implements PayOrderService {


    private static final Logger logger = LoggerFactory.getLogger(PayOrderOrderServiceImpl.class);
    @Resource
    private Config config;
    @Resource
    private PayOrderMapper payOrderMapper;


    public List<PayOrder> selectOneOrderByCode(String code) throws Exception {
        Example example = new Example(PayOrder.class);
        example.createCriteria().andEqualTo("code", code);
        return payOrderMapper.selectByExample(example);
    }

    public List<PayOrder> selectAllPayOrders() throws Exception {
        return payOrderMapper.selectAll();
    }

    public List<PayOrder> selectPendingReceivePayOrders(PayOrder payOrder) throws Exception {
        Example example = new Example(PayOrder.class);
        example.createCriteria()
                .andEqualTo("payStatus", payOrder.getPayStatus())
                .andLessThan("retryCount", payOrder.getRetryCount())
                .andEqualTo("status", Status.enable);
        return payOrderMapper.selectByExample(example);
    }

    public int insertOnePayOrder(PayOrder payOrder) throws Exception {
        return payOrderMapper.insert(payOrder);
    }

    public int updateByPrimaryKeySelectivePayOrder(PayOrder payOrder) throws Exception {
        return payOrderMapper.updateByPrimaryKeySelective(payOrder);
    }

    @Transactional
    public String savePayOrderProcess(PayOrder payOrder) throws Exception {
        String url = String.format("http://%s:%s@%s:%s", config.getRpcuser()
                , config.getRpcpassword()
                , config.getRpcaddress()
                , config.getRpcport());
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("id", 0);
        jsonParam.put("method", "getaccountaddress");
        JSONArray jsonArray = new JSONArray();
        String code = getCode();
        jsonArray.add(code);
        jsonParam.put("params", jsonArray);
        JSONObject jsonObject = HttpClientUtils.jsonPost(url, jsonParam, null, null, null);
        if (jsonObject != null) {
            payOrder.setReceiveAddress(jsonObject.getString("result"));
        }
        payOrder.setRetryCount(0);
        payOrder.setCode(code);
        payOrder.setCreateTime(new Date());
        payOrder.setPayStatus(PayStatus.pending);
        payOrder.setStatus(Status.enable);
        this.insertOnePayOrder(payOrder);
        return code;
    }

    /**
     * (取得code)锁定容易造成并发问题的资源
     *
     * @return
     */
    public synchronized String getCode() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    public void updatePayOrderPayStatus() throws Exception {
        PayOrder payOrder = new PayOrder();
        payOrder.setStatus(PayStatus.pending + 1);
        payOrder.setRetryCount(config.getMaxretryCount());
        List<PayOrder> pendingReceivePayOrders = selectPendingReceivePayOrders(payOrder);
        for (PayOrder pendingReceivePayOrder : pendingReceivePayOrders) {
            logger.info("[start task:]{}",pendingReceivePayOrder);
            if (isPaid(pendingReceivePayOrder.getReceiveAddress(), pendingReceivePayOrder.getAmount())) {
                PayOrder paidOrder = new PayOrder();
                paidOrder.setId(pendingReceivePayOrder.getId());
                paidOrder.setPayStatus(PayStatus.paid);
                paidOrder.setPayTime(new Date());
                paidOrder.setLastRetryTime(new Date());
                updateByPrimaryKeySelectivePayOrder(paidOrder);
                //回调地址一定要签名，暂不管
                String callbackUrl = pendingReceivePayOrder.getCallbackUrl() + String.format("?code=%s&orderId=%s&payStatus=%s"
                        , pendingReceivePayOrder.getCode()
                        , pendingReceivePayOrder.getOrderId()
                        , PayStatus.paid
                );
                doCallback(callbackUrl, null, null, null, null);
            } else {
                PayOrder pendingOrder = new PayOrder();
                pendingOrder.setId(pendingReceivePayOrder.getId());
                pendingOrder.setRetryCount(pendingReceivePayOrder.getRetryCount() + 1);
                pendingOrder.setLastRetryTime(new Date());
                if (pendingReceivePayOrder.getRetryCount() + 1 >= 10) {
                    pendingOrder.setStatus(Status.disable);
                }
                updateByPrimaryKeySelectivePayOrder(pendingOrder);
                //回调地址一定要签名，暂不管
                String callbackUrl = pendingReceivePayOrder.getCallbackUrl() + String.format("?code=%s&orderId=%s&payStatus=%s"
                        , pendingReceivePayOrder.getCode()
                        , pendingReceivePayOrder.getOrderId()
                        , PayStatus.paid
                );
                doCallback(callbackUrl, null, null, null, null);
            }
        }
    }

    public boolean isPaid(String receiveAddress, BigDecimal amount) throws Exception {
        try {
            String url = String.format("http://%s:%s@%s:%s", config.getRpcuser()
                    , config.getRpcpassword()
                    , config.getRpcaddress()
                    , config.getRpcport());
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id", 0);
            jsonParam.put("method", "getreceivedbyaddress");
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(receiveAddress);
            jsonArray.add(config.getValidationLevel());
            jsonParam.put("params", jsonArray);

            JSONObject jsonObject = HttpClientUtils.jsonPost(url, jsonParam, null, null, null);
            if (jsonObject.getBigDecimal("result").compareTo(amount) == 1) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            logger.error("[isPaid Exception]:exception message:{}",e.getMessage());
        }
        return false;
    }

    @Async
    public void doCallback(String callbackUrl, JSONObject jsonParam, Integer connectionRequestTimeout, Integer connectTimeout, Integer socketTimeout) {
        try {
            HttpClientUtils.jsonPost(callbackUrl, jsonParam, null, null, null);
        } catch (Exception e) {
            logger.error("[callback Exception]:callbackUrl:{}jsonParam:{}exception message:{}", callbackUrl,jsonParam,e.getMessage());
        }
    }

}
