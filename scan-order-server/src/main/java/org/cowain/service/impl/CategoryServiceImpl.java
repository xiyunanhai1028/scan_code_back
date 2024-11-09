package org.cowain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.cowain.constant.MessageConstant;
import org.cowain.constant.StatusConstant;
import org.cowain.dto.CategoryDTO;
import org.cowain.dto.CategoryPageQueryDTO;
import org.cowain.entity.Category;
import org.cowain.exception.DeletionNotAllowedException;
import org.cowain.mapper.CategoryMapper;
import org.cowain.mapper.DishMapper;
import org.cowain.mapper.SetmealMapper;
import org.cowain.result.PageResult;
import org.cowain.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    //新增分类
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        //拷贝数据
        BeanUtils.copyProperties(categoryDTO, category);
        //设置分类默认状态，默认禁用
        category.setStatus(StatusConstant.DISABLE);

//        //设置创建时间，更新时间，创建人，更新人
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
//        category.setCreateUser(BaseContext.getCurrentId());
//        category.setUpdateUser(BaseContext.getCurrentId());
        //插入操作
        categoryMapper.insert(category);
    }

    //分页分类数据查询
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        //1.开启分类
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        //2.下一条sql进行分页，自动加入limit关键字分页
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    //启用/禁用分类
    public void startOrStop(Integer status, Long id) {
        Category category = Category.builder()
                .id(id)
                .status(status)
//                .updateUser(BaseContext.getCurrentId())
//                .updateTime(LocalDateTime.now())
                .build();
        categoryMapper.update(category);
    }

    //删除分类
    public void deleteById(Long id) {
        //查询当前分类是否关联了菜品，如果关联了就抛出业务异常
        Integer count = dishMapper.countByCategory(id);
        if (count > 0) {
            //当前分类下有菜品,不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        count = setmealMapper.countByCategory(id);
        if (count > 0) {
            //当前分类下有套餐,不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.deleteById(id);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        categoryMapper.update(category);
    }

    //根据类型获取菜品分类列表
    public List<Category> getListByType(int type) {
       List<Category> categoryList= categoryMapper.list(type);
       return categoryList;
    }

    //根据类型查询分类列表
    public List<Category> list(Integer type) {
        List<Category> list = categoryMapper.list(type);
        return list;
    }

    @Override
    public List<Category> getListAll() {
        List<Category> list = categoryMapper.listAll();
        return list;
    }
}
