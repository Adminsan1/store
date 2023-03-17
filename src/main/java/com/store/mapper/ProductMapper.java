package com.store.mapper;

import com.store.entity.Product;

import java.util.List;

/**
 * @author 32153
 */
public interface ProductMapper {
    /**
     * 查询热销商品的前四名
     * @return 热销商品前四名的集合
     */
    List<Product> findHotList();
    Product findById(Integer id);
}
