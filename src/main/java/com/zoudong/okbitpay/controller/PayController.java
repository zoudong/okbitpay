package com.zoudong.okbitpay.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zoudong.okbitpay.model.PayOrder;
import com.zoudong.okbitpay.config.Config;
import com.zoudong.okbitpay.service.PayOrderService;
import com.zoudong.okbitpay.util.ResultUtils;
import com.zoudong.okbitpay.util.result.BaseResult;
import com.zoudong.okbitpay.util.result.PageResult;
import com.zoudong.okbitpay.util.result.Result;
import com.zoudong.okbitpay.validate.PayOrderCreateGroup;
import com.zoudong.okbitpay.vo.PayOrderVOBase;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PayController {
    private static final Logger logger = LoggerFactory.getLogger(PayController.class);
    @Resource
    private Config config;
    @Resource
    private PayOrderService payOrderService;

    @RequestMapping(value = "/createPayOrder", method = RequestMethod.POST)
    public Object createPayOrder(@Validated(value = {PayOrderCreateGroup.class})PayOrderVOBase payOrderVO, BindingResult bindingResult) {
        Result result=new Result();
        try {
            logger.info("start{}", payOrderVO);
            //入参校验
            if (bindingResult.hasErrors()) {
                result = ResultUtils.fillParameterfail(bindingResult);
                logger.info("入参校验错误:{}", result);
                return result;
            }

            PayOrder payOrder=new PayOrder();
            ConvertUtils.register(new DateConverter(null), java.util.Date.class);
            BeanUtils.copyProperties(payOrder,payOrderVO);
            String code=payOrderService.savePayOrderProcess(payOrder);
            JSONObject resultObject=new JSONObject();
            resultObject.put("code",code);
            result = ResultUtils.fillSuccessData(resultObject);
            logger.info("end{}", result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.fillErrorMsg("创建bitCoin支付订单失败");
        }
    }

    @RequestMapping(value = "/selectAllPayOrder", method = RequestMethod.GET)
    public PageResult<PayOrder> queryApprovalUpIntegration(PayOrder payOrder) {
        try {
            logger.info("start{}", payOrder);
            PageHelper.startPage(payOrder.getStart(), payOrder.getLength());
            List<PayOrder> list = payOrderService.selectAllPayOrders();
            PageResult<PayOrder> pageResult = new PageResult<PayOrder>(list);
            pageResult.setStatus(BaseResult.success);
            pageResult.setMsg(BaseResult.success);
            logger.info("end{}", pageResult);
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult<>();
        }
    }
}
