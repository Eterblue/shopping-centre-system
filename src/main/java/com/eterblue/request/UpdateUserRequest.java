package com.eterblue.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel("用户更新信息")
public class UpdateUserRequest {

    private String name;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "1[3,4,5,6,7,8,9]\\d{9}$",message = "请输入正确的手机号")
    private String phone;

    private String password;

    private Integer gender;

}
