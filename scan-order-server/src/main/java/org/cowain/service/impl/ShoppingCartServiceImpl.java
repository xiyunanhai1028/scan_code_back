package org.cowain.service.impl;

import org.cowain.context.BaseContext;
import org.cowain.dto.ShoppingCartDTO;
import org.cowain.entity.Dish;
import org.cowain.entity.Setmeal;
import org.cowain.entity.ShoppingCart;
import org.cowain.mapper.DishMapper;
import org.cowain.mapper.SetmealMapper;
import org.cowain.mapper.ShoppingCartMapper;
import org.cowain.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    //添加购物车
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        //判断当前加入到购物车中的商品是否已经存在了
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);

        shoppingCart.setSitNum(shoppingCartDTO.getSitNum());
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        if (list != null && list.size() > 0) {
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(cart);
        } else {
            Long dishId = shoppingCartDTO.getDishId();
            if (dishId != null) {
                Dish dish = dishMapper.queryById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            } else {
                Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmeal = setmealMapper.queryById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            Long userId = BaseContext.getCurrentId();
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }

    }

    @Override
    public List<ShoppingCart> showShoppingCart(Integer sitNum) {
//        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .sitNum(sitNum)
                .build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }

    //清空购物车
    public void cleanShoppingCart(Integer sitNum) {
//        Long userId = BaseContext.getCurrentId();
//        shoppingCartMapper.deleteByUserId(userId);
        shoppingCartMapper.deleteBySitNum(sitNum);
    }

    //删除一个商品
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        //设置查询条件，查询当前登录用户的购物车数据
//        shoppingCart.setUserId(BaseContext.getCurrentId());
        shoppingCart.setSitNum(shoppingCartDTO.getSitNum());
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if (list != null && list.size() > 0) {
            ShoppingCart cart = list.get(0);
            Integer number = cart.getNumber();
            if (number == 1) {
                //当前商品在购物车中的份数为1，直接删除这条记录
                shoppingCartMapper.deleteById(cart.getId());
            } else {
                //当前商品在购物车中的份数大于1，修改份数
                cart.setNumber(number - 1);
                shoppingCartMapper.updateNumberById(cart);
            }
        }
    }
}
