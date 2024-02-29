package com.eterblue.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("用户更新信息")
public class UpdateUserRequest {

    private Long id;

    private String name;

    private String password;

    private Integer gender;

}
