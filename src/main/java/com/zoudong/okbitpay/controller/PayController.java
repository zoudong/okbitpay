package com.zoudong.okbitpay.controller;

import com.github.pagehelper.PageHelper;
import com.zoudong.okbitpay.model.PayOrder;
import com.zoudong.okbitpay.po.Config;
import com.zoudong.okbitpay.service.PayOrderService;
import com.zoudong.okbitpay.util.ResultUtils;
import com.zoudong.okbitpay.util.result.BaseResult;
import com.zoudong.okbitpay.util.result.PageResult;
import com.zoudong.okbitpay.util.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class PayController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PayController.class);
    @Resource
    private Config config;
    @Resource
    private PayOrderService payOrderService;

    @ResponseBody
    @RequestMapping(value = "/insertOnePayOrder", method = RequestMethod.POST)
    public Object insertOnePayOrder(PayOrder payOrder) {
        try {
            LOGGER.info("start{}", payOrder);
            payOrderService.savePayOrderProcess(payOrder);
            Result result = ResultUtils.fillSuccessData(null);
            LOGGER.info("end{}", result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.fillErrorMsg("创建bitCoin支付订单失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/selectAllPayOrder", method = RequestMethod.GET)
    public PageResult<PayOrder> queryApprovalUpIntegration(PayOrder payOrder) {
        try {
            LOGGER.info("start{}", payOrder);
            PageHelper.startPage(payOrder.getStart(), payOrder.getLength());
            List<PayOrder> list = payOrderService.selectAllPayOrders();
            PageResult<PayOrder> pageResult = new PageResult<PayOrder>(list);
            pageResult.setStatus(BaseResult.success);
            pageResult.setMsg(BaseResult.success);
            LOGGER.info("end{}", pageResult);
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult<>();
        }
    }
}
