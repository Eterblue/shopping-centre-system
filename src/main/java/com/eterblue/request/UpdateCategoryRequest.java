package com.eterblue.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("更新分类传入的数据")
public class UpdateCategoryRequest {

    private Long id;

    private Integer rank;

    private String name;
}
