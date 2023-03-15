package com.store.serveice;

import com.store.entity.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 32153
 */
@Transactional
public interface IUserService {
    /**
     * 用户注册
     * @param user 用户数据
     */
    void reg(User user);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户数据
     */
    User login(String username, String password);

    void changPassword(Integer uid,String username,String oldPassword,String newPassword);
}
