package com.zoudong.okbitpay.vo;

import com.zoudong.okbitpay.validate.PayOrderCreateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PayOrderVO extends BasePageVO {

    private Long id;

    private String code;
    @NotNull(message = "{amount not null}", groups = {PayOrderCreateGroup.class})
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
    @NotEmpty(message = "{callbackUrl not empty}", groups = {PayOrderCreateGroup.class})
    private String callbackUrl;
    @NotEmpty(message = "{orderId not empty}", groups = {PayOrderCreateGroup.class})
    private String orderId;


}