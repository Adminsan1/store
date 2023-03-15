package com.store;

import com.store.entity.User;
import com.store.mapper.UserMapper;
import com.store.serveice.IUserService;
import com.store.serveice.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {StoreApplication.class})
public class StoreApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserService userService;

    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("Admin2565");
            user.setPassword("000");
            userService.reg(user);
            System.out.println("添加OK");
        } catch (ServiceException e) {
            //获取类的对象和名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login() {
        try {
            String username = "Admin2565";
            String password = "000";
            User user = userService.login(username, password);
            System.out.println("登录成功！" + user);
        } catch (ServiceException e) {
            System.out.println("登录失败！" + e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void updatePasswordByUid() {
        try {
            Integer uid = 3;
            String username = "adminq";
            String oldPassword = "888888";
            String newPassword = "321";
            userService.changPassword(uid, username, oldPassword, newPassword);
            System.out.println();
        } catch (ServiceException e) {
            System.out.println("密码修改失败！" + e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void findByUid() {
        System.out.println(userMapper.findByUid(1));
//        Integer uid = 7;
//        User result = userMapper.findByUid(uid);
//        System.out.println(result);
    }
}
