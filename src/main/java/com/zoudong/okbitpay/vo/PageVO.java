package com.zoudong.okbitpay.vo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
public class PageVO implements Serializable {


    private Integer start = 0;


    private Integer length = 10;


    private String ordBy;


    private String ordCol;


}
