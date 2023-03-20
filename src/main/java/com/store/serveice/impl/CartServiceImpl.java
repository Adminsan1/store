package com.store.serveice.impl;

import com.store.entity.Cart;
import com.store.entity.Product;
import com.store.mapper.CartMapper;
import com.store.mapper.ProductMapper;
import com.store.serveice.ICartService;
import com.store.serveice.ex.CartNotFoundException;
import com.store.serveice.ex.InsertException;
import com.store.vo.CartVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 处理购物车数据的业务层实现类
 *
 * @author 32153
 */
@Service
public class CartServiceImpl implements ICartService {
    @Resource
    private CartMapper cartMapper;
    @Resource
    private ProductMapper productMapper;

    @Override
    public List<CartVo> findVoByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        // 根据参数pid和uid查询购物车中的数据
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        Date date = new Date();
        // 判断查询结果是否为null
        if (result == null) {
            // 是：表示该用户并未将该商品添加到购物车
            // 创建Cart对象
            Cart cart = new Cart();
            // 封装数据：uid,pid,amount
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            // 调用productService.findById(pid)查询商品数据，得到商品价格
            Product product = productMapper.findById(pid);
            // 封装数据：price
            cart.setPrice(product.getPrice());
            // 封装数据：4个日志
            cart.setCreatedUser(username);
            cart.setCreatedTime(date);
            cart.setModifiedUser(username);
            cart.setModifiedTime(date);
            // 调用insert(cart)执行将数据插入到数据表中
            Integer rows = cartMapper.insert(cart);
            if (rows != 1) {
                throw new InsertException("插入商品数据时出现未知错误，请联系系统管理员");
            }
        } else {
            // 否：表示该用户的购物车中已有该商品
            // 从查询结果中获取购物车数据的id
            // 从查询结果中取出原数量，与参数amount相加，得到新的数量
            Integer num = result.getNum() + amount;
            // 执行更新数量
            Integer rows = cartMapper.updateNumByCid(result.getCid(), num, username, date);
            if (rows != 1) {
                throw new InsertException("修改商品数量时出现未知错误，请联系系统管理员");
            }
        }
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        // 调用findByCid(cid)根据参数cid查询购物车数据
        Cart result = cartMapper.findByCid(cid);
        // 创建当前时间对象，作为modifiedTime
        Date date = new Date();
        // 判断查询结果是否为null
        if (result == null) {
            throw new CartNotFoundException("尝试访问的购物车数据不存在");
        }
        // 判断查询结果中的uid与参数uid是否不一致
        if (!result.getUid().equals(uid)) {
            try {
                throw new AccessDeniedException("非法访问");
            } catch (AccessDeniedException e) {
                e.printStackTrace();
            }
        }
        // 可选：检查商品的数量是否大于多少(适用于增加数量)或小于多少(适用于减少数量)
        // 根据查询结果中的原数量增加1得到新的数量num
        Integer num = result.getNum() + 1;
        // 调用updateNumByCid执行修改数量
        Integer rows = cartMapper.updateNumByCid(cid, num, username, date);
        if (rows != 1) {
            throw new InsertException("修改商品数量时出现未知错误");
        }
        return num;
    }

    @Override
    public List<CartVo> getVOByCid(Integer uid, Integer[] cids) {
        List<CartVo> voByCid = cartMapper.findVOByCid(cids);
        Iterator<CartVo> iterator = voByCid.iterator();
        while (iterator.hasNext()) {
            CartVo next = iterator.next();
            if (!next.getUid().equals(uid)) {
                voByCid.remove(iterator);
            }
        }
        return voByCid;
    }
}
