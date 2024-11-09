package org.cowain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.cowain.constant.MessageConstant;
import org.cowain.dto.DishDTO;
import org.cowain.dto.DishPageQueryDTO;
import org.cowain.entity.Dish;
import org.cowain.entity.DishFlavor;
import org.cowain.exception.DeletionNotAllowedException;
import org.cowain.mapper.DishFlavorMapper;
import org.cowain.mapper.DishMapper;
import org.cowain.mapper.SetmealDishMapper;
import org.cowain.mapper.SetmealMapper;
import org.cowain.result.PageResult;
import org.cowain.service.DishService;
import org.cowain.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
//    @Autowired
//    private SetmealMapper setmealMapper;

    //保存菜品以及口味
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        //保存菜品
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //模式新增的菜品是禁用状态
        dish.setStatus(0);
        dishMapper.insert(dish);
        //保存菜品对应的口味
        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            //向口味表插入n条数据
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    //分页查询菜品
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        //开启分页
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        //查询
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    //根据菜品ID查询详情
    public DishVO dishById(Long id) {
        //查询菜品
        Dish dish = dishMapper.queryById(id);
        //根据dishId查询对应的口味
        List<DishFlavor> dishFlavors = dishFlavorMapper.queryByDishId(id);
        //构建VO
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    //修改菜品
    @Transactional
    public void updateWithFlavor(DishDTO dishDTO) {
        //跟新菜品
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);

        //删除菜品对应的所有口味
        Long dishId = dishDTO.getId();
        dishFlavorMapper.deleteByDishId(dishId);
        //新增菜品新的口味
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    //删除菜品
    @Transactional
    public void deleteBatchWithFlavor(List<Long> ids) {
        //1.不能删除启用的菜品
        List<Dish> dishList = dishMapper.queryByIds(ids);
        for (Dish dish : dishList) {
            Integer status = dish.getStatus();
            if (status == 1) {//启用中
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //2.不能删除套餐所用的菜品
        List<Long> setmealIds = setmealDishMapper.queryByDishId(ids);
        if (setmealIds != null && setmealIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        //3.删除菜品
        dishMapper.deleteBatchByIds(ids);
        //4.删除菜品对应的口味
        dishFlavorMapper.deleteBatchByDishIds(ids);
    }

    //启售/停售菜品
    public void updateStatus(Integer status, Long id) {
        Dish dish = dishMapper.queryById(id);
        dish.setStatus(status);
        dishMapper.update(dish);
    }

    //根据分类ID查询菜品
    public List<Dish> listByCategoryId(Long categoryId) {
        List<Dish> list = dishMapper.listByCategoryId(categoryId);
        return list;
    }

    //条件查询菜品和口味信息
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.listByCategoryIdAndEnable(dish.getCategoryId());
        List<DishVO> dishVOList = new ArrayList<>();
        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d, dishVO);

            //根据菜品ID查询对应的口味数据
            List<DishFlavor> dishFlavors = dishFlavorMapper.queryByDishId(d.getId());
            dishVO.setFlavors(dishFlavors);
            dishVOList.add(dishVO);
        }
        return dishVOList;
    }
}
