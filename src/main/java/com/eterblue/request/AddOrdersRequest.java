package com.eterblue.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;


@Data
public class AddOrdersRequest {
    @NotNull(message = "收货地址不为空")
    private String address;

    @Range(min = 1,max = 4,message = "只能填写1~4")
    @NotNull(message = "配送方式不能为空")
    private Integer deliveryMethod;

}
