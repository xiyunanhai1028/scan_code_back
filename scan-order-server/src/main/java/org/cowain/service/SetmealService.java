package org.cowain.service;

import org.cowain.dto.SetmealDTO;
import org.cowain.dto.SetmealPageQueryDTO;
import org.cowain.entity.Setmeal;
import org.cowain.result.PageResult;
import org.cowain.vo.DishItemVO;
import org.cowain.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    //分页查询套餐数据
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    //保存套餐
    void saveWithDish(SetmealDTO setmealDTO);

    //根据套餐ID获取套餐详情
    SetmealVO getByIdWithDish(Long id);

    //更新套餐
    void update(SetmealDTO setmealDTO);

    //批量删除套餐
    void deleteBatch(List<Long> ids);

    //停售/启售套餐
    void updateStatus(Integer status, Long id);

    //条件查询
    List<Setmeal> list(Setmeal setmeal);

    //根据ID查询菜品选项
    List<DishItemVO> getDishItemById(Long id);
}
