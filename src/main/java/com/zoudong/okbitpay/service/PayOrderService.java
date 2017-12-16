package com.zoudong.okbitpay.service;

import com.zoudong.okbitpay.model.PayOrder;

import java.util.List;
import java.util.Map;


public interface PayOrderService {
    public List<PayOrder> selectOneOrderByCode(String code) throws Exception;

    public List<PayOrder> selectAllPayOrders() throws Exception;

    public List<PayOrder> selectPendingReceivePayOrders(PayOrder payOrder) throws Exception;

    public int insertOnePayOrder(PayOrder payOrder) throws Exception;

    public int updateByPrimaryKeySelectivePayOrder(PayOrder payOrder) throws Exception;

    public Map<String,String> savePayOrderProcess(PayOrder payOrder) throws Exception;

    public void updatePayOrderPayStatus() throws Exception;

}
