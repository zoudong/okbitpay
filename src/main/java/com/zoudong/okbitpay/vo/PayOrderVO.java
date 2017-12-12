package com.zoudong.okbitpay.vo;

import com.zoudong.okbitpay.validate.PayOrderCreateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PayOrderVO extends BasePageVO {

    private Long id;

    private String code;
    @NotNull(message = "{amount not null}", groups = {PayOrderCreateGroup.class})
    private BigDecimal amount;
    @Size(max=255)
    private String receiveAddress;
    @Size(max=255)
    private String sendAddress;

    private Date payTime;

    private Integer retryCount;

    private Date lastRetryTime;
    @Size(max=255)
    private String payStatus;
    @Size(max=255)
    private String payDescription;

    private Long productId;
    @Size(max=255)
    private String productName;

    private Long productNumber;
    @Size(max=255)
    private String accountCode;
    @Size(max=255)
    private String accountName;
    @Size(max=255)
    private String status;

    private Date createTime;

    private Date updateTime;
    @NotEmpty(message = "{callbackUrl not empty}", groups = {PayOrderCreateGroup.class})
    private String callbackUrl;
    @NotEmpty(message = "{orderId not empty}", groups = {PayOrderCreateGroup.class})
    private String orderId;


}