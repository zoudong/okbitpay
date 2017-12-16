package com.zoudong.okbitpay.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zoudong.okbitpay.config.Config;
import com.zoudong.okbitpay.model.PayOrder;
import com.zoudong.okbitpay.service.PayOrderService;
import com.zoudong.okbitpay.util.ResultUtils;
import com.zoudong.okbitpay.util.result.BaseResult;
import com.zoudong.okbitpay.util.result.PageResult;
import com.zoudong.okbitpay.util.result.Result;
import com.zoudong.okbitpay.validate.PayOrderCreateGroup;
import com.zoudong.okbitpay.vo.PayOrderVO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/bitcoinPayment")
@RestController
public class PayController {
    private static final Logger logger = LoggerFactory.getLogger(PayController.class);
    @Resource
    private Config config;
    @Resource
    private PayOrderService payOrderService;

    @RequestMapping(value = "/createPayOrder", method = RequestMethod.POST)
    public Object createPayOrder(@Validated(value = {PayOrderCreateGroup.class}) PayOrderVO payOrderVO, BindingResult bindingResult) {
        Result result = new Result();
        try {
            logger.info("[start createPayOrder]:{}", payOrderVO);
            //入参校验
            if (bindingResult.hasErrors()) {
                result = ResultUtils.fillParameterfail(bindingResult);
                logger.info("[parameterfail]:{}", result);
                return result;
            }
            PayOrder payOrder = new PayOrder();
            ConvertUtils.register(new DateConverter(null), java.util.Date.class);
            BeanUtils.copyProperties(payOrder, payOrderVO);
            String code = payOrderService.savePayOrderProcess(payOrder);
            JSONObject resultObject = new JSONObject();
            resultObject.put("code", code);
            result = ResultUtils.fillSuccessData(resultObject);
            logger.info("[end createPayOrder]:{}", result);
            return result;
        } catch (Exception e) {
            logger.error("[createPayOrder unknown Exception]");
            e.printStackTrace();
            return ResultUtils.fillErrorMsg("createPayOrder unknown Exception");
        }
    }

    @RequestMapping(value = "/selectPayOrderByPage", method = RequestMethod.POST)
    public PageResult<PayOrder> selectPayOrderByPage(PayOrder payOrder) {
        try {
            logger.info("[start selectPayOrderByPage]:{}", payOrder);
            PageHelper.startPage(payOrder.getStart(), payOrder.getLength());
            List<PayOrder> list = payOrderService.selectAllPayOrders();
            PageResult<PayOrder> pageResult = new PageResult<PayOrder>(list);
            pageResult.setStatus(BaseResult.success);
            pageResult.setMsg(BaseResult.success);
            logger.info("[end selectPayOrderByPage]{}", pageResult);
            return pageResult;
        } catch (Exception e) {
            logger.error("[selectPayOrderByPage unknown Exception]");
            e.printStackTrace();
            return new PageResult<>();
        }
    }

    @RequestMapping(value = "/selectOneOrderByCode", method = RequestMethod.POST)
    public Result selectOneOrderByCode(String code) {
        try {
            logger.info("[start selectOneOrderByCode]:{}", code);
            if(StringUtils.isEmpty(code)){
                return ResultUtils.fillErrorMsg("[parameterfail]:code not is empty!");
            }
            PayOrder payOrder= payOrderService.selectOneOrderByCode(code);
            logger.info("[end selectOneOrderByCode]{}", payOrder);
            return ResultUtils.fillSuccessData(payOrder);
        } catch (Exception e) {
            logger.error("[selectOneOrderByCode unknown Exception]");
            e.printStackTrace();
            return  ResultUtils.fillErrorMsg(e.getMessage());
        }
    }
}
