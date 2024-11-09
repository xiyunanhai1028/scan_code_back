package org.cowain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.cowain.constant.MessageConstant;
import org.cowain.dto.SetmealDTO;
import org.cowain.dto.SetmealPageQueryDTO;
import org.cowain.entity.Setmeal;
import org.cowain.entity.SetmealDish;
import org.cowain.exception.DeletionNotAllowedException;
import org.cowain.mapper.SetmealDishMapper;
import org.cowain.mapper.SetmealMapper;
import org.cowain.result.PageResult;
import org.cowain.service.SetmealService;
import org.cowain.vo.DishItemVO;
import org.cowain.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    //分页查询套餐数据
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        //开启分页
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        //查询
        Page<SetmealVO> setmealVO = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(setmealVO.getTotal(), setmealVO.getResult());
    }

    //保存套餐
    @Transactional
    public void saveWithDish(SetmealDTO setmealDTO) {
        //保存套餐
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setStatus(0);
        setmealMapper.insert(setmeal);

        //保存菜品与套餐关系数据
        //获取保存后的setmealId
        Long setmealId = setmeal.getId();
        List<SetmealDish> dishList = setmealDTO.getSetmealDishes();
        if (dishList != null && dishList.size() > 0) {
            dishList.forEach(dish -> {
                dish.setSetmealId(setmealId);
            });
            setmealDishMapper.insertBatch(dishList);
        }

    }

    //根据套餐ID获取套餐详情
    public SetmealVO getByIdWithDish(Long id) {
        //获取套餐数据
        SetmealVO setmealVO = setmealMapper.getByIdWithDish(id);
        return setmealVO;
    }

    //更新套餐
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        //更新套餐
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);
        //删除所有的套餐与菜品关系
        setmealDishMapper.deleteBatch(setmeal.getId());
        //新增套餐与菜品关系
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && setmealDishes.size() > 0) {
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmeal.getId());
            });
            setmealDishMapper.insertBatch(setmealDishes);
        }

    }

    //批量删除套餐
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //启用中的套餐不能被删除
        List<Setmeal> setmeals = setmealMapper.queryBatchByIds(ids);
        for (Setmeal setmeal : setmeals) {
            Integer status = setmeal.getStatus();
            if(status==1){
                throw  new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        ids.forEach(setmealId->{
            //删除套餐
            setmealMapper.delete(setmealId);
            setmealDishMapper.deleteBatch(setmealId);
        });
    }

    //停售/启售套餐
    public void updateStatus(Integer status, Long id) {
       Setmeal setmeal =  setmealMapper.queryById(id);
       setmeal.setStatus(status);
       //更新
        setmealMapper.update(setmeal);
    }


    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    //根据id查询菜品选项
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
