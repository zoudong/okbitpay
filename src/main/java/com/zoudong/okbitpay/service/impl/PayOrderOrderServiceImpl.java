package com.zoudong.okbitpay.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zoudong.okbitpay.dao.PayOrderMapper;
import com.zoudong.okbitpay.model.PayOrder;
import com.zoudong.okbitpay.po.Config;
import com.zoudong.okbitpay.service.PayOrderService;
import com.zoudong.okbitpay.util.http.HttpClientUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
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
                .andLessThan("retryCount", payOrder.getRetryCount());
        return payOrderMapper.selectByExample(example);
    }

    public int insertOnePayOrder(PayOrder payOrder) throws Exception {
        return payOrderMapper.insert(payOrder);
    }

    public int updateByPrimaryKeySelectivePayOrder(PayOrder payOrder) throws Exception {
        return payOrderMapper.updateByPrimaryKeySelective(payOrder);
    }

    @Transactional
    public String savePayOrderProcess(PayOrder payOrder) throws Exception{
        String url=String.format("http://%s:%s@%s:%s",config.getRpcuser()
                ,config.getRpcpassword()
                ,config.getRpcaddress()
                ,config.getRpcport());
        JSONObject jsonParam=new JSONObject();
        //{"id": 0, "method": "getaccountaddress", "params":["1234567"]}
        jsonParam.put("id",0);
        jsonParam.put("method","getaccountaddress");
        JSONArray jsonArray=new JSONArray();
        String orderId= UUID.randomUUID().toString();
        jsonArray.add(orderId);
        jsonParam.put("params",jsonArray);
        JSONObject jsonObject=HttpClientUtils.jsonPost(url,jsonParam,null,null,null);
        if(jsonObject!=null) {
            payOrder.setReceiveAddress(jsonObject.getString("result"));
        }else {
            throw new Exception("创建比特币收款地址失败！");
        }
        payOrder.setRetryCount(0l);
        payOrder.setCode(orderId);
        payOrder.setCreateTime(new Date());
        payOrder.setPayStatus("pending");
        payOrder.setStatus("enable");
        this.insertOnePayOrder(payOrder);
        return orderId;
    }

}
