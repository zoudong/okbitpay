package com.zoudong.okbitpay;

import com.zoudong.okbitpay.service.PayOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OkbitpayApplicationTests {
    @Resource
	private PayOrderService payOrderService;
	@Test
	public void testUpdatePayOrderPayStatus()throws Exception {
		//payOrderService.updatePayOrderPayStatus();
	}
}
