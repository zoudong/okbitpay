package com.zoudong.okbitpay.service.impl;

import com.zoudong.okbitpay.dao.PayOrderMapper;
import com.zoudong.okbitpay.model.PayOrder;
import com.zoudong.okbitpay.service.PayOrderService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PayOrderOrderServiceImpl implements PayOrderService {
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


}
