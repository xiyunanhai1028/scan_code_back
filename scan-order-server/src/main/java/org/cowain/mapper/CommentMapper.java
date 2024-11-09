package org.cowain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.cowain.annotation.AutoFill;
import org.cowain.entity.Comment;
import org.cowain.enumeration.OperationType;

import java.util.List;

@Mapper
public interface CommentMapper {
    void insert(Comment comment);

    @Select("select * from comment")
    List<Comment> queryAllComments();

    @Select("select * from comment where LOCATE(#{dishId},dish_ids) order by create_time desc")
    List<Comment> queryByDishId(Long dishId);

    @Select("select * from comment where LOCATE(#{setmealId},setmeal_ids) order by create_time desc")
    List<Comment> queryBySetmealId(Long setmealId);

}
