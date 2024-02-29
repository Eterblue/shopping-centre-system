package com.eterblue.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.util.List;

@Data
@ApiModel("分页查询结果")
public class PageVO <T>{

    private Long total;

    private Long pages;
    private List<T> list;
}
