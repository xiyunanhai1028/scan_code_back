package org.cowain.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cowain.constant.StatusConstant;
import org.cowain.entity.Setmeal;
import org.cowain.result.Result;
import org.cowain.service.SetmealService;
import org.cowain.vo.DishItemVO;
import org.cowain.vo.SetmealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "C端-套餐接口")
@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @ApiOperation("根据分类ID查询套餐")
    @GetMapping("/list")
    @Cacheable(cacheNames = "setmealCache",key = "#categoryId")//key: setmealCache::categoryId
    public Result<List<Setmeal>> list(Long categoryId) {
        //售卖且对应categoryId
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);

        List<Setmeal> list = setmealService.list(setmeal);
        return Result.success(list);
    }

    @ApiOperation("根据套餐ID查询包含的菜品列表")
    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> dishList(@PathVariable("id") Long id){
        List<DishItemVO> list = setmealService.getDishItemById(id);
        return Result.success(list);
    }
}
