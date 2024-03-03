package com.eterblue.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel("用户请求登录")
public class LoginUserRequest {

    @Pattern(regexp = "1[3,4,5,6,7,8,9]\\d{9}$",message = "请输入正确的手机号")
    @NotNull(message = "手机号不为空")
    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不为空")
    private String password;

}
