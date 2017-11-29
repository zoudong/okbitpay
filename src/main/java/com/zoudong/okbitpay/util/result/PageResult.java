package com.zoudong.okbitpay.util.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> extends BaseResult implements Serializable {
    public PageResult() {
        super();
    }

    private int draw; // 第几次请求
    private int start = 0;// 起止位置'0'
    private int length = 10; // 数据长度'10'
    @JsonProperty(value = "iTotalRecords")
    private long iTotalRecords;
    @JsonProperty(value = "iTotalDisplayRecords")
    private long iTotalDisplayRecords;


    public PageResult(List<T> data) {
        PageInfo<T> pageInfo = new PageInfo<T>(data);
        this.start = pageInfo.getStartRow();
        this.length = pageInfo.getPageSize();
        this.iTotalRecords = pageInfo.getTotal();
        this.iTotalDisplayRecords = iTotalRecords;
        setData(pageInfo.getList());
    }


}
