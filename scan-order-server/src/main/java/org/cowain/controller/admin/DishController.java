package org.cowain.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.cowain.dto.DishDTO;
import org.cowain.dto.DishPageQueryDTO;
import org.cowain.entity.Dish;
import org.cowain.result.PageResult;
import org.cowain.result.Result;
import org.cowain.service.DishService;
import org.cowain.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @ApiOperation("保存菜品")
    @PostMapping
    @CacheEvict(cacheNames = "dishCache",key = "#dishDTO.categoryId")
    public Result<String> save(@RequestBody DishDTO dishDTO) {
        log.info("保存菜品参数：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success("保存成功");
    }

    @ApiOperation("分页查询菜品")
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询菜品：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("查询菜品详情")
    @GetMapping("/{id}")
    public Result<DishVO> dishById(@PathVariable Long id) {
        log.info("查询ID：{}", id);
        DishVO dishVO = dishService.dishById(id);
        return Result.success(dishVO);
    }

    @ApiOperation("修改菜品")
    @PutMapping
    @CacheEvict(cacheNames = "dishCache",allEntries = true)
    public Result<String> update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品:{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success("修改成功");
    }

    @ApiOperation("删除菜品")
    @DeleteMapping
    @CacheEvict(cacheNames = "dishCache",allEntries = true)
    public Result<String> delete(@RequestParam List<Long> ids) {
        log.info("删除菜品:{}", ids);
        dishService.deleteBatchWithFlavor(ids);
        return Result.success("删除成功");
    }

    @ApiOperation("启售/停售菜品")
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "dishCache",allEntries = true)
    public Result<String> updateStatus(@PathVariable Integer status, Long id) {
        log.info("启售/停售菜品:{},{}", status, id);
        dishService.updateStatus(status, id);
        return Result.success(status == 1 ? "启售成功" : "停售成功");
    }

    @ApiOperation("根据分类ID查询对应的菜品")
    @GetMapping("/list")
    public Result<List<Dish>> list(Long categoryId) {
        log.info("根据分类ID查询对应的菜品:{}", categoryId);
        List<Dish> dishList = dishService.listByCategoryId(categoryId);
        return Result.success(dishList);
    }
}
