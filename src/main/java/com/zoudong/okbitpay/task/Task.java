package com.zoudong.okbitpay.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class Task {

    private static final Logger LOGGER = LoggerFactory.getLogger(Task.class);

    @Scheduled(cron = "0 0/3 * * * ? ")
    public void closeTimeA() throws Exception {
        LOGGER.info("********回执扫描********");
    }


    public static int compare_date(Date dt1, Date dt2)throws Exception {


            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }

    }
}
