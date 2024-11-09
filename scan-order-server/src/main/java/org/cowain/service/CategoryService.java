package org.cowain.service;

import org.cowain.dto.CategoryDTO;
import org.cowain.dto.CategoryPageQueryDTO;
import org.cowain.entity.Category;
import org.cowain.result.PageResult;

import java.util.List;

public interface CategoryService {
    //新增分类
    void save(CategoryDTO categoryDTO);

    //分页分类数据查询
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    //启用/禁用分类
    void startOrStop(Integer status, Long id);

    //删除分类
    void deleteById(Long id);

    //修改分类
    void update(CategoryDTO categoryDTO);

    //根据类型获取菜品分类列表
    List<Category> getListByType(int type);

    //根据类型查询分类列表
    List<Category> list(Integer type);

    List<Category> getListAll();

}
