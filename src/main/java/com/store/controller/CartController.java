package com.store.controller;

import com.store.serveice.ICartService;
import com.store.util.JsonResult;
import com.store.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController {
    @Autowired
    private ICartService cartService;

    @PostMapping("add_to_cart")
    public JsonResult<Void> addToCat(Integer pid, Integer amount, HttpSession session) {
        cartService.addToCart(
                getUidFromSession(session),
                pid, amount,
                getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    @GetMapping({"", "/"})
    public JsonResult getVoByUid(HttpSession session) {
        List<CartVo> data = cartService.findVoByUid(getUidFromSession(session));
        return new JsonResult<>(OK, data);
    }

    @PostMapping("{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        // 从Session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务对象执行增加数量
        Integer data = cartService.addNum(cid, uid, username);
        // 返回成功
        return new JsonResult<>(OK, data);
    }

    @GetMapping({"list"})
    public JsonResult<List<CartVo>> getVoByCid(Integer[] cids,HttpSession session) {
        List<CartVo> data = cartService.getVOByCid(getUidFromSession(session),cids);
        return new JsonResult<>(OK, data);
    }
}
