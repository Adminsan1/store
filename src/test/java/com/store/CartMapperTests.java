package com.store;

import com.store.entity.Cart;
import com.store.mapper.CartMapper;
import com.store.vo.CartVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest(classes = {StoreApplication.class})
public class CartMapperTests {
    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert() {
        Cart cart = new Cart();
        cart.setUid(2);
        cart.setPid(2);
        cart.setNum(5);
        cart.setPrice(10L);
        Integer rows = cartMapper.insert(cart);
        System.out.println("rows=" + rows);
    }

    @Test
    public void updateNumByCid() {
        Integer cid = 3;
        Integer num = 10;
        String modifiedUser = "购物车管理员";
        Date modifiedTime = new Date();
        Integer rows = cartMapper.updateNumByCid(cid, num, modifiedUser, modifiedTime);
        System.out.println("rows=" + rows);
    }

    @Test
    public void findByUidAndPid() {
        Integer uid =2;
        Integer pid = 1;
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        System.out.println(result);
    }
    @Test
    public void findVOByUid() {
        List<CartVo> list = cartMapper.findVOByUid(1);
        System.out.println(list);
    }
    @Test
    public void findByCid() {
        Integer cid = 6;
        Cart result = cartMapper.findByCid(cid);
        System.out.println(result);
    }
    @Test
    public void findVOByCid() {
        Integer[] cids={4,5,6};
        System.out.println(cartMapper.findVOByCid(cids));
    }
}
