package com.eterblue.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel("返回订单数据")
@Builder
public class OrdersVO {

    private Long id;

    private Integer deliveryMethod;

    private BigDecimal amount;

    private LocalDateTime createTime;

}
