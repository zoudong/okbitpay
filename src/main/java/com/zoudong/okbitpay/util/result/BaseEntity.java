package com.zoudong.okbitpay.util.result;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {
    @Id
    private Long id;

    @Transient
    private Integer start = 0;

    @Transient
    private Integer length = 10;

    @Transient
    private String ordBy;

    @Transient
    private String ordCol;


}
