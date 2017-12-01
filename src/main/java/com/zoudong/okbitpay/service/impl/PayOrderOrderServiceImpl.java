package com.zoudong.okbitpay.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zoudong.okbitpay.dao.PayOrderMapper;
import com.zoudong.okbitpay.model.PayOrder;
import com.zoudong.okbitpay.po.Config;
import com.zoudong.okbitpay.po.PayStatus;
import com.zoudong.okbitpay.po.Status;
import com.zoudong.okbitpay.service.PayOrderService;
import com.zoudong.okbitpay.util.http.HttpClientUtils;
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
    @Resource
    private Config config;
    @Resource
    private PayOrderMapper payOrderMapper;

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
        String orderId = UUID.randomUUID().toString();
        jsonArray.add(orderId);
        jsonParam.put("params", jsonArray);
        JSONObject jsonObject = HttpClientUtils.jsonPost(url, jsonParam, null, null, null);
        if (jsonObject != null) {
            payOrder.setReceiveAddress(jsonObject.getString("result"));
        }
        payOrder.setRetryCount(0);
        payOrder.setCode(orderId);
        payOrder.setCreateTime(new Date());
        payOrder.setPayStatus(PayStatus.pending);
        payOrder.setStatus(Status.enable);
        this.insertOnePayOrder(payOrder);
        return orderId;
    }


    public void updatePayOrderPayStatus() throws Exception {
        PayOrder payOrder = new PayOrder();
        payOrder.setStatus(PayStatus.pending);
        payOrder.setRetryCount(10);
        List<PayOrder> pendingReceivePayOrders = selectPendingReceivePayOrders(payOrder);
        for (PayOrder pendingReceivePayOrder : pendingReceivePayOrders) {
            if (isPaid(pendingReceivePayOrder.getReceiveAddress(),pendingReceivePayOrder.getAmount())) {
                PayOrder paidOrder = new PayOrder();
                paidOrder.setId(pendingReceivePayOrder.getId());
                paidOrder.setPayStatus(PayStatus.paid);
                paidOrder.setPayTime(new Date());
                paidOrder.setLastRetryTime(new Date());
                updateByPrimaryKeySelectivePayOrder(paidOrder);
            } else {
                PayOrder pendingOrder = new PayOrder();
                pendingOrder.setId(pendingReceivePayOrder.getId());
                pendingOrder.setRetryCount(pendingReceivePayOrder.getRetryCount() + 1);
                if(pendingReceivePayOrder.getRetryCount() + 1>=10){
                    pendingOrder.setStatus(Status.disable);
                }
                pendingOrder.setLastRetryTime(new Date());
                updateByPrimaryKeySelectivePayOrder(pendingOrder);
            }
        }
    }

    public boolean isPaid(String receiveAddress,BigDecimal amount) throws Exception {
        String url = String.format("http://%s:%s@%s:%s", config.getRpcuser()
                , config.getRpcpassword()
                , config.getRpcaddress()
                , config.getRpcport());
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("id", 0);
        jsonParam.put("method", "getreceivedbyaddress");
        JSONArray jsonArray = new JSONArray();
        String orderId = UUID.randomUUID().toString();
        jsonArray.add(receiveAddress);
        jsonArray.add(config.getValidationLevel());
        jsonParam.put("params", jsonArray);

        JSONObject jsonObject = HttpClientUtils.jsonPost(url, jsonParam, null, null, null);
        if (amount.equals(jsonObject.getBigDecimal("result"))) {
            return true;
        } else {
            return false;
        }
    }


}
