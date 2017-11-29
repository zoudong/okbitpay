package com.zoudong.okbitpay.service.impl;

import com.zoudong.okbitpay.dao.PayOrderMapper;
import com.zoudong.okbitpay.model.PayOrder;
import com.zoudong.okbitpay.service.PayOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PayOrderOrderServiceImpl implements PayOrderService {
    @Resource
    private PayOrderMapper payOrderMapper;

    public List<PayOrder> selectAll()throws Exception{
        return payOrderMapper.selectAll();
    }
}
