package com.eterblue.service;

import com.eterblue.model.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eterblue.request.LoginUserRequest;
import com.eterblue.request.UpdateUserRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 *
 */
public interface IUserService extends IService<User> {

    User loginUser(LoginUserRequest loginUserRequest);

    void updateUser(UpdateUserRequest updateUserRequest);
}
