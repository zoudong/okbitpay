package com.zoudong.okbitpay.task;

import com.zoudong.okbitpay.service.PayOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@EnableScheduling
public class Task {

    private static final Logger LOGGER = LoggerFactory.getLogger(Task.class);
    @Resource
    private PayOrderService payOrderService;

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void closeTimeA() throws Exception {
        LOGGER.info("********回执扫描开始********");
        payOrderService.updatePayOrderPayStatus();
        LOGGER.info("********回执扫描结束********");
    }

}
