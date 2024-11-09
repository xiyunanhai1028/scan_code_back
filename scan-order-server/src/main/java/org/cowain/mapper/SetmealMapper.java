package org.cowain.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.cowain.annotation.AutoFill;
import org.cowain.dto.SetmealPageQueryDTO;
import org.cowain.entity.Setmeal;
import org.cowain.enumeration.OperationType;
import org.cowain.result.PageResult;
import org.cowain.vo.DishItemVO;
import org.cowain.vo.SetmealVO;

import java.util.List;

@Mapper
public interface SetmealMapper {
    @Select("select count(id) from setmeal where category_id = #{id}")
    Integer countByCategory(Long id);

    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setmeal);

    SetmealVO getByIdWithDish(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

    List<Setmeal> queryBatchByIds(List<Long> ids);

    @Delete("delete from setmeal where  id = #{setmealId}")
    void delete(Long setmealId);

    @Select("select * from setmeal where  id = #{id}")
    Setmeal queryById(Long id);

    List<Setmeal> list(Setmeal setmeal);

    @Select("select sd.name,sd.copies,d.image,d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);
}
