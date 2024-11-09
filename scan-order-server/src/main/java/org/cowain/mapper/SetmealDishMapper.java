package org.cowain.mapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.cowain.entity.SetmealDish;

import java.util.List;


@Mapper
public interface SetmealDishMapper {
    void insertBatch(List<SetmealDish> dishList);

    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteBatch(Long setmealId);

    List<Long> queryByDishId(List<Long> dishIds);
}
