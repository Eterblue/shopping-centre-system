package com.eterblue.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("增加分类传入数据")
public class AddCategoryRequest {

    private Long id;

    private Integer level;

    private Long parentId;

    private String name;

    private Integer rank;
}
