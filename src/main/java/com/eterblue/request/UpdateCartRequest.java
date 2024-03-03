package com.eterblue.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@ApiModel("更新购物车传入数据")
public class UpdateCartRequest {

    @ApiModelProperty("0为减少 1为增加")
    @Range(min = 0,max = 1,message = "输入0或1")
    private Integer quantity;

    @Range(min = 0,max = 1,message = "输入0或1")
    private Integer isSelected;

    @Range(min = 0,max = 1,message = "输入0或1")
    private Integer status;

    @Range(min = 1,message = "商品id最小为1")
    private Long productId;

    @ApiModelProperty("购物车id")
    private Long id;
}
