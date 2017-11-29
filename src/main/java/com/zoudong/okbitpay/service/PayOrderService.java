package com.zoudong.okbitpay.service;

import com.zoudong.okbitpay.model.PayOrder;
import java.util.List;


public interface PayOrderService {
   public List<PayOrder> selectAll()throws Exception;
}
