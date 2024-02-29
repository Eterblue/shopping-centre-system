package com.eterblue.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;


@Data
@ApiModel("分页查询")
public class PageQueryRequest {

    private Integer pageNumber=1;

    private Integer pageSize=5;

    private String sortBy;

    private Boolean asc=true;


}
