package org.cowain.mapper;

import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.cowain.annotation.AutoFill;
import org.cowain.dto.DishPageQueryDTO;
import org.cowain.entity.Category;
import org.cowain.entity.Dish;
import org.cowain.enumeration.OperationType;
import org.cowain.service.DishService;
import org.cowain.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Mapper
public interface DishMapper {

    @Select("select count(id) from dish where category_id = #{id}")
    Integer countByCategory(Long id);

    @AutoFill(OperationType.INSERT)
    //自动填充时间
    void insert(Dish dish);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    @Select("select * from dish where id = #{id}")
    Dish queryById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    List<Dish> queryByIds(List<Long> ids);

    void deleteBatchByIds(List<Long> ids);

    @Select("select * from dish where category_id = #{categoryId}")
    List<Dish> listByCategoryId(Long categoryId);

    @Select("select * from dish where category_id = #{categoryId} and status = 1")
    List<Dish> listByCategoryIdAndEnable(Long categoryId);
}
