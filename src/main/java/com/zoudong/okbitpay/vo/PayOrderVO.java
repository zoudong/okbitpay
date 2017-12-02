package com.zoudong.okbitpay.vo;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PayOrderVO extends PageVO {

    private Long id;

    private String code;
    @NotNull
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
    @NotEmpty
    private String callbackUrl;
    @NotEmpty
    private String orderId;


}