package com.store.serveice;

import com.store.entity.User;
import com.store.vo.CartVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** 处理商品数据的业务层接口
 * @author 32153*/
@Transactional
public interface ICartService {
    /**
     * 将商品添加到购物车
     * @param uid 当前登录用户的id
     * @param pid 商品的id
     * @param amount 增加的数量
     * @param username 当前登录的用户名
     */
    void addToCart(Integer uid, Integer pid, Integer amount, String username);

    List<CartVo> findVoByUid(Integer uid);

    Integer addNum(Integer cid,Integer uid,String username);

    List<CartVo> getVOByCid(Integer uid,Integer[] cids);
}
