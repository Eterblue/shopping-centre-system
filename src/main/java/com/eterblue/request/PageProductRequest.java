package com.eterblue.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("分页查询商品")
public class PageProductRequest {

    private String name;

    private Long categoryId;

    private String sortBy;

    private Boolean asc=true;

    private Integer pageNumber=1;

    private Integer pageSize=5;

}
