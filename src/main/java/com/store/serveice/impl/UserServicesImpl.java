package com.store.serveice.impl;

import com.store.entity.User;
import com.store.mapper.UserMapper;
import com.store.serveice.IUserService;
import com.store.serveice.ex.*;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @author 32153
 */
@Service
public class UserServicesImpl implements IUserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 注册加密
     */
    @Override
    public void reg(User user) {
        // 根据参数user对象获取注册的用户名
        String username = user.getUsername();
        // 调用持久层的User findByUsername(String username)方法，根据用户名查询用户数据
        User result = userMapper.findByUsername(username);
        // 判断查询结果是否不为null
        if (result != null) {
            // 是：表示用户名已被占用，则抛出UsernameDuplicateException异常
            throw new UsernameDuplicatedException("尝试注册的用户名[" + username + "]已经被占用");
        }
        // 创建当前时间对象
        Date now = new Date();
        // 补全数据：加密后的密码
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMD5Password(user.getPassword(), salt);
        user.setPassword(md5Password);
        // 补全数据：盐值
        user.setSalt(salt);
        // 补全数据：isDelete(0)
        user.setIsDelete(0);
        // 补全数据：4项日志属性
        user.setCreatedUser(username);
        user.setCreatedTime(now);
        user.setModifiedUser(username);
        user.setModifiedTime(now);

        // 表示用户名没有被占用，则允许注册
        // 调用持久层Integer insert(User user)方法，执行注册并获取返回值(受影响的行数)
        Integer rows = userMapper.insert(user);
        // 判断受影响的行数是否不为1
        if (rows != 1) {
            // 是：插入数据时出现某种错误，则抛出InsertException异常
            throw new InsertException("添加用户数据出现未知错误，请联系系统管理员");
        }
    }

    /**
     * 登录
     */
    @Override
    public User login(String username, String password) {
        // 调用userMapper的findByUsername()方法，根据参数username查询用户数据
        User result = userMapper.findByUsername(username);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在的错误");
        }

        // 判断查询结果中的isDelete是否为1
        if (result.getIsDelete() == 1) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在的错误");
        }

        // 从查询结果中获取盐值
        String salt = result.getSalt();
        // 调用getMd5Password()方法，将参数password和salt结合起来进行加密
        String md5Password = getMD5Password(password, salt);
        // 判断查询结果中的密码，与以上加密得到的密码是否不一致
        if (!result.getPassword().equals(md5Password)) {
            // 是：抛出PasswordNotMatchException异常
            throw new PasswordNotMathException("密码验证失败的错误");
        }

        // 创建新的User对象
        User user = new User();
        // 将查询结果中的uid、username、avatar封装到新的user对象中
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        // 返回新的user对象
        return user;
    }


    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        // 调用userMapper的findByUsername()方法，根据参数username查询用户数据
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户不存在");
        }

        // 从查询结果中取出盐值
        String salt = result.getSalt();
        // 将参数oldPassword结合盐值加密，得到oldMd5Password
        String oldMd5Password = getMD5Password(oldPassword, salt);
        // 判断查询结果中的password与oldMd5Password是否不一致
        if (!result.getPassword().contentEquals(oldMd5Password)) {
            // 是：抛出PasswordNotMatchException异常
            throw new PasswordNotMathException("原密码错误");
        }
        // 将参数newPassword结合盐值加密，得到newMd5Password
        String newMd5Password = getMD5Password(newPassword, salt);
        // 创建当前时间对象
        Date now = new Date();
        // 调用userMapper的updatePasswordByUid()更新密码，并获取返回值
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, now);
        // 判断以上返回的受影响行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException异常
            throw new UpdateException("更新用户数据时出现未知错误");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 判断查询结果中的isDelete是否为1
        if (result.getIsDelete().equals(1)) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 创建新的User对象
        User user = new User();
        // 将以上查询结果中的username/phone/email/gender封装到新User对象中
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        // 返回新的User对象
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 判断查询结果中的isDelete是否为1
        if (result.getIsDelete().equals(1)) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 向参数user中补全数据：uid
        user.setUid(uid);
        // 向参数user中补全数据：modifiedUser(username)
        user.setModifiedUser(username);
        // 向参数user中补全数据：modifiedTime(new Date())
        user.setModifiedTime(new Date());
        // 调用userMapper的updateInfoByUid(User user)方法执行修改，并获取返回值
        Integer rows = userMapper.updateInfoByUid(user);
        // 判断以上返回的受影响行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException异常
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        //查询当前用户数据是否存在
        User byUid = userMapper.findByUid(uid);
        if (byUid == null || byUid.getIsDelete().equals(1)) {
            throw new UserNotFoundException("查询当前用户数据不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid,avatar,username,new Date());
        if (rows != 1) {
            throw new UpdateException("更新用户头像发生错误");
        }
    }

    /**
     * md5加密
     */
    private String getMD5Password(String password, String salt) {
        //加密算法加密
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password).getBytes()).toUpperCase();
        }
        return password;
    }
}
