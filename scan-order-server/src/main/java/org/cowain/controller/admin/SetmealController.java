package org.cowain.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cowain.dto.SetmealDTO;
import org.cowain.dto.SetmealPageQueryDTO;
import org.cowain.result.PageResult;
import org.cowain.result.Result;
import org.cowain.service.SetmealService;
import org.cowain.vo.SetmealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐相关接口")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @ApiOperation("套餐分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }



    @ApiOperation("获取套餐详情")
    @GetMapping("/{id}")
    public Result<SetmealVO> getById(@PathVariable("id") Long id) {
        log.info("获取套餐详情:{}", id);
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }

    @ApiOperation("保存套餐")
    @PostMapping
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")
    public Result<String> save(@RequestBody SetmealDTO setmealDTO) {
        log.info("保存套餐:{}", setmealDTO);
        setmealService.saveWithDish(setmealDTO);
        return Result.success("保存成功");
    }

    @ApiOperation("更新套餐")
    @PutMapping
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result<String> update(@RequestBody SetmealDTO setmealDTO) {
        log.info("更新套餐:{}", setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success("更新成功");
    }

    @ApiOperation("删除套餐")
    @DeleteMapping
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result<String> delete(@RequestParam List<Long> ids) {
        log.info("删除套餐:{}", ids);
        setmealService.deleteBatch(ids);
        return Result.success("删除成功");
    }

    @ApiOperation("停售/启售")
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result<String> updateStatus(@PathVariable Integer status, Long id) {
        log.info("停售/启售:{},{}", status, id);
        setmealService.updateStatus(status,id);
        return Result.success(status == 1 ? "启售成功" : "停售成功");
    }
}
