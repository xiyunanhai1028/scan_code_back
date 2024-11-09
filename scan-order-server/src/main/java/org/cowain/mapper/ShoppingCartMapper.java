package org.cowain.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.cowain.entity.ShoppingCart;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart cart);

    @Insert("insert into shopping_cart(name, sit_num,image,  dish_id, setmeal_id, dish_flavor, amount, create_time,user_id) " +
            "values (#{name}, #{sitNum},#{image}, #{dishId}, #{setmealId}, #{dishFlavor}, #{amount}, #{createTime},#{userId})")
    void insert(ShoppingCart shoppingCart);

    @Delete("delete from shopping_cart where user_id = #{userId}")
    void deleteByUserId(Long userId);

    @Delete("delete from shopping_cart where id = #{id}")
    void deleteById(Long id);

    @Delete("delete from shopping_cart where sit_num = #{sitNum}")
    void deleteBySitNum(Integer sitNum);
}
