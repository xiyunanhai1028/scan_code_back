package org.cowain.service;


import org.cowain.dto.DishDTO;
import org.cowain.dto.DishPageQueryDTO;
import org.cowain.entity.Dish;
import org.cowain.result.PageResult;
import org.cowain.vo.DishVO;

import java.util.List;

public interface DishService {

    //保存菜品以及口味
    void saveWithFlavor(DishDTO dishDTO);

    //分页查询菜品
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    //根据菜品ID查询详情
    DishVO dishById(Long id);

    //修改菜品
    void updateWithFlavor(DishDTO dishDTO);

    //删除菜品
    void deleteBatchWithFlavor(List<Long> ids);

    //启售/停售菜品
    void updateStatus(Integer status, Long id);

    //根据分类ID查询菜品
    List<Dish> listByCategoryId(Long categoryId);

    //条件查询菜品和口味信息
    List<DishVO> listWithFlavor(Dish dish);
}
