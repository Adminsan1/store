package com.store.controller;

import com.store.entity.User;
import com.store.serveice.IUserService;
import com.store.serveice.ex.*;
import com.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 32153
 */
@RestController
@RequestMapping("users")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        // 调用业务对象执行注册
        userService.reg(user);
        return new JsonResult<Void>(OK);
    }

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        // 调用业务对象的方法执行登录，并获取返回值
        User data = userService.login(username, password);

        //登录成功后，将uid和username存入到HttpSession中
        session.setAttribute("uid", data.getUid());
        //获取sesion中的数据
        session.setAttribute("username", data.getUsername());

        // System.out.println("Session中的uid=" + getUidFromSession(session));
        // System.out.println("Session中的username=" + getUsernameFromSession(session));

        //  将以上返回值和状态码OK封装到响应结果中并返回
        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("update")
    public JsonResult<Void> update(String oldPassword, String newPassword, HttpSession session) {
        Integer integer = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(integer, username, oldPassword, newPassword);
        return new JsonResult<Void>(OK);
    }

    @GetMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        // 从HttpSession对象中获取uid
        Integer uid = getUidFromSession(session);
        // 调用业务对象执行获取数据
        User data = userService.getByUid(uid);
        // 响应成功和数据
        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        // 从HttpSession对象中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务对象执行修改用户资料
        userService.changeInfo(uid, username, user);
        // 响应成功
        return new JsonResult<>(OK);
    }

    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    public static final List<String> AVATAR_TYPE = new ArrayList();

    static {
        AVATAR_TYPE.add("images/jpeg");
        AVATAR_TYPE.add("images/png");
        AVATAR_TYPE.add("images/bmp");
        AVATAR_TYPE.add("images/gif");
        AVATAR_TYPE.add("images/jpg");
    }

    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, @RequestParam("file") MultipartFile file) {
        // 从HttpSession对象中获取uid和username
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件超过限制");
        }
        String type = file.getContentType();
        if (!AVATAR_TYPE.contains(type)) {
            throw new FileTypeException("文件类型不支持");
        }
        String parent = session.getServletContext().getRealPath("upload");
        File dir = new File(parent);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String originalFilename = file.getOriginalFilename();
        int dotIndex = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(dotIndex);
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        File dest = new File(dir, filename);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        }
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        String avatar = "/upload" + filename;
        userService.changeAvatar(uid, username, avatar);
        return new JsonResult<String>(OK,avatar);
    }
}
