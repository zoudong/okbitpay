package com.zoudong.okbitpay.model;

import com.zoudong.okbitpay.util.result.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class PayOrder extends BaseEntity{

    private String code;

    private BigDecimal amount;

    private String receiveAddress;

    private String sendAddress;

    private Date payTime;

    private Integer retryCount;

    private Date lastRetryTime;

    private String payStatus;

    private String payDescription;

    private Long productId;

    private String productName;

    private Long productNumber;

    private String accountCode;

    private String accountName;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String callbackUrl;

    private String orderId;


}