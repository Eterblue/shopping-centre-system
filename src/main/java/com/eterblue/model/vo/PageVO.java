package com.eterblue.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("分页查询结果")
public class PageVO <T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long total;

    private Long pages;

    private List<T> list;
}
