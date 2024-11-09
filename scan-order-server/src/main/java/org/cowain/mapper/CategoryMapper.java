package org.cowain.mapper;

import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.cowain.annotation.AutoFill;
import org.cowain.dto.CategoryPageQueryDTO;
import org.cowain.entity.Category;
import org.cowain.enumeration.OperationType;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "values " +
            "(#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(OperationType.INSERT)
//注入自定义注解
    void insert(Category category);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    @AutoFill(OperationType.UPDATE)
    void update(Category category);

    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    @Select("select * from category where type = #{type}")
    List<Category> getListByType(int type);

    List<Category> list(Integer type);

    @Select("select * from category where status = 1")
    List<Category> listAll();
}
