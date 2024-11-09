package org.cowain.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cowain.constant.StatusConstant;
import org.cowain.entity.Dish;
import org.cowain.result.Result;
import org.cowain.service.DishService;
import org.cowain.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "C端-菜品相关接口")
@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @ApiOperation("根据分类ID查询菜品")
    @GetMapping("/list")
    @Cacheable(cacheNames = "dishCache",key = "#categoryId") // key: dishCache::categoryId
    public Result<List<DishVO>> list(Long categoryId) {
        //查询的是启售中的菜品
        Dish dish = new Dish();
        dish.setStatus(StatusConstant.ENABLE);
        dish.setCategoryId(categoryId);
        //查询的是带有口味信息的启售中的菜品
        List<DishVO> list = dishService.listWithFlavor(dish);
        return Result.success(list);
    }
}
