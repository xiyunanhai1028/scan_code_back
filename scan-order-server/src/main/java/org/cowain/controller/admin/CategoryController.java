package org.cowain.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cowain.dto.CategoryDTO;
import org.cowain.dto.CategoryPageQueryDTO;
import org.cowain.entity.Category;
import org.cowain.result.PageResult;
import org.cowain.result.Result;
import org.cowain.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation("分类数据保存")
    @PostMapping()
    public Result<String> save(@RequestBody CategoryDTO categoryDTO) {
        categoryService.save(categoryDTO);
        return Result.success("保存成功");
    }

    @ApiOperation("分类列表数据分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @ApiModelProperty("启用/禁用分类")
    @PostMapping("/status/{status}")
    public Result<String> startOrStop(@PathVariable("status") Integer status, Long id) {
        categoryService.startOrStop(status, id);
        return Result.success("成功");
    }

    @ApiModelProperty("删除分类")
    @DeleteMapping
    public Result<String> deleteById(Long id) {
        log.info("删除分类：{}", id);
        categoryService.deleteById(id);
        return Result.success("删除成功");
    }

    @ApiOperation("修改分类")
    @PutMapping
    public Result<String> update(@RequestBody CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
        return Result.success("修改成功");
    }

    @ApiOperation("获取分类列表")
    @GetMapping("/list")
    public Result<List<Category>> list(int type) {
        List<Category> list = categoryService.getListByType(type);
        return Result.success(list);
    }

    @ApiOperation("获取分类列表所有")
    @GetMapping("/all/list")
    public Result<List<Category>> listAll() {
        List<Category> list = categoryService.getListAll();
        return Result.success(list);
    }
}
