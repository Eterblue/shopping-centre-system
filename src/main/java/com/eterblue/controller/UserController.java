package com.eterblue.controller;


import cn.hutool.core.bean.BeanUtil;
import com.eterblue.model.pojo.User;
import com.eterblue.model.vo.LoginUserVO;
import com.eterblue.request.LoginUserRequest;
import com.eterblue.request.UpdateUserRequest;
import com.eterblue.response.BaseResponse;
import com.eterblue.service.IUserService;
import com.eterblue.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 *
 */
@Slf4j
@RestController
@RequestMapping("/shop/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Api(tags = "用户相关接口")
public class UserController {

    private final IUserService userService;

    @ApiOperation("登录注册接口")
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> login(@RequestBody @Valid LoginUserRequest loginUserRequest){

        log.info("用户登录请求:{}",loginUserRequest);
        User user = userService.loginUser(loginUserRequest);

        Map<String,Object> claims=new HashMap<>();
        claims.put("id",user.getId());
        claims.put("phone",user.getPhone());
        String token = JwtUtil.getToken(claims);

        LoginUserVO loginUserVO= LoginUserVO.builder()
                .id(user.getId())
                .name(user.getName())
                .phone(user.getPhone())
                .token(token)
                .build();

        return BaseResponse.success(loginUserVO);
    }

    @ApiOperation("更新用户信息")
    @PutMapping("/update")
    public BaseResponse update(@RequestBody @Valid UpdateUserRequest updateUserRequest){

        userService.updateUser(updateUserRequest);
        return BaseResponse.success();
    }

    @ApiOperation("注销账户")
    @DeleteMapping("/delete/id")
    public BaseResponse delete(Long id){
        userService.removeById(id);
        return BaseResponse.success();
    }

    @ApiOperation("查询回显")
    @GetMapping("/{id}")
    public BaseResponse<User> getById(@PathVariable Long id){
        User user = userService.getById(id);
        return BaseResponse.success(user);
    }
}
