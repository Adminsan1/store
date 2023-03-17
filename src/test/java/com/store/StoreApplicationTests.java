package com.store;

import com.store.entity.User;
import com.store.mapper.UserMapper;
import com.store.serveice.IUserService;
import com.store.serveice.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

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
            user.setUsername("Admin");
            user.setPassword("123");
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
            Integer uid = 2;
            String username = "Admin1";
            String oldPassword = "000";
            String newPassword = "123";
            userService.changePassword(uid, username, oldPassword, newPassword);
            System.out.println();
        } catch (ServiceException e) {
            System.out.println("密码修改失败！" + e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void findByUid() {
        System.out.println(userMapper.findByUid(1));
    }

    @Test
    public void updateInfoByUid() {
        User user = new User();
        user.setUid(2);
        user.setPhone("17858802222");
        user.setEmail("admin@cy.com");
        user.setGender(0);
        user.setModifiedUser("系统管理员");
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        System.out.println("rows=" + rows);
    }



    @Test
    public void getByUid() {
        try {
            Integer uid = 2;
            User user = userService.getByUid(uid);
            System.out.println(user);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void changeInfo() {
        try {
            Integer uid = 3;
            String username = "数据管理员";
            User user = new User();
            user.setPhone("15846451542");
            user.setEmail("admin03@qq.com");
            user.setGender(0);
            userService.changeInfo(uid, username, user);
            System.out.println("OK.");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
}
