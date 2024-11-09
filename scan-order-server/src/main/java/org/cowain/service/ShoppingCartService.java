package org.cowain.service;

import org.cowain.dto.ShoppingCartDTO;
import org.cowain.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    //添加购物车
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);
    //查询购物车
    List<ShoppingCart> showShoppingCart(Integer sitNum);
    //清空购物车
    void cleanShoppingCart(Integer sitNum);
    //删除一个商品
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
