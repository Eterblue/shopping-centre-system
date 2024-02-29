package com.eterblue.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "用户登录后返回的数据")
public class LoginUserVO {

    @ApiModelProperty("主键值")
    private Long id;

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("jwt令牌")
    private String token;
}
