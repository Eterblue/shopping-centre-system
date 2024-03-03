package com.eterblue.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.eterblue.model.pojo.User;
import com.eterblue.mapper.UserMapper;
import com.eterblue.request.LoginUserRequest;
import com.eterblue.request.UpdateUserRequest;
import com.eterblue.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eterblue.util.ThreadUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User loginUser(LoginUserRequest loginUserRequest) {

        User user = BeanUtil.copyProperties(loginUserRequest, User.class);
        List<User> list = lambdaQuery().eq(User::getPhone, user.getPhone()).list();

        //对密码进行加密
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        if(list.isEmpty()){
            //用户未注册,进行注册
            user.setName("用户"+System.currentTimeMillis());
            user.setPassword(password);
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            save(user);
        }
        else {
            //手机号和密码匹配失败
            if(!list.get(0).getPassword().equals(password)){
                throw new RuntimeException("账号或密码错误");
            }
        }

        return lambdaQuery().eq(User::getPhone, user.getPhone()).list().get(0);
    }

    @Override
    @Transactional
    public void updateUser(UpdateUserRequest updateUserRequest) {

        User user = BeanUtil.copyProperties(updateUserRequest, User.class);
        Long userId = ThreadUtil.getCurrentId();
        String phone = user.getPhone();
        String password = user.getPassword();
        //1.更改手机号
        if(!phone.isEmpty()){
            //根据手机号查询
            User user1 = lambdaQuery().eq(User::getPhone, phone).list().get(0);
            //查询到用户，并且用户id不等抛出异常
            if(user1!=null && !user1.getId().equals(userId)) throw new RuntimeException("该手机号已被注册");
        }
        //2.更改密码
        if(!password.isEmpty()){
            password = DigestUtils.md5DigestAsHex(password.getBytes());
        }
        lambdaUpdate()
                .set(!user.getName().isEmpty(),User::getName,user.getName())
                .set(!password.isEmpty(),User::getPassword, password)
                .set(!phone.isEmpty(),User::getPhone, phone)
                .set(user.getGender()!=null,User::getGender,user.getGender())
                .set(User::getUpdateTime,LocalDateTime.now())
                .eq(User::getId,userId)
                .update();
    }
}
