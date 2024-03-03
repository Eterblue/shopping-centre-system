package com.eterblue.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("增加分类传入数据")
public class AddCategoryRequest {


    @Range(min = 1,max = 2,message = "层级只能为1或2")
    private Integer level;

    @Range(min = 1,message = "父分类id不符要求")
    private Long parentId;

    @NotNull(message = "分类名称不为空")
    private String name;

    @Range(min = 0,message = "rank最小为0")
    private Integer rank;
}
