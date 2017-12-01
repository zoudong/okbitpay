package com.zoudong.okbitpay.service;

import com.zoudong.okbitpay.model.PayOrder;

import java.util.List;


public interface PayOrderService {

    public List<PayOrder> selectAllPayOrders() throws Exception;

    public List<PayOrder> selectPendingReceivePayOrders(PayOrder payOrder) throws Exception;

    public int insertOnePayOrder(PayOrder payOrder) throws Exception;

    public int updateByPrimaryKeySelectivePayOrder(PayOrder payOrder) throws Exception;

    public String savePayOrderProcess(PayOrder payOrder) throws Exception;

    public void updatePayOrderPayStatus() throws Exception;

}
