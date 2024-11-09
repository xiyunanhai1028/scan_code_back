package org.cowain.service;


import org.cowain.dto.CommentDTO;
import org.cowain.entity.Comment;

import java.util.List;

public interface CommentService {
    void save(CommentDTO commentDTO);

    List<Comment> listByDishIdOrSetmealId(Long dishId, Long setmealId);
}
