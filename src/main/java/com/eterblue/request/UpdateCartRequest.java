package com.eterblue.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("更新购物车传入数据")
public class UpdateCartRequest {

    @ApiModelProperty("0为减少 1为增加")
    private Integer quantity;

    private Integer isSelected;

    private Integer status;

    private Long productId;
}
