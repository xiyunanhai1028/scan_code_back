package org.cowain.service.impl;

import org.cowain.context.BaseContext;
import org.cowain.dto.CommentDTO;
import org.cowain.entity.Comment;
import org.cowain.entity.Orders;
import org.cowain.entity.User;
import org.cowain.mapper.CommentMapper;
import org.cowain.mapper.OrderMapper;
import org.cowain.mapper.UserMapper;
import org.cowain.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;

    //添加评论
    @Transactional
    public void save(CommentDTO commentDTO) {
        Long userId = BaseContext.getCurrentId();
        //1.查询用户信息
        User user = userMapper.getById(userId);
        //2.插入评论
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setUserId(userId);
        comment.setCreateTime(LocalDateTime.now());
        comment.setMobile(user.getPhone());
        commentMapper.insert(comment);
        //3.修改订单状态
        Orders order = orderMapper.getOrderById(commentDTO.getOrderId());
        order.setIsComment(1);
        orderMapper.update(order);
    }

    //查询菜品或套餐的评论
    public List<Comment> listByDishIdOrSetmealId(Long dishId, Long setmealId) {
        List<Comment> comments = null;
        if (dishId != null) {
            comments = commentMapper.queryByDishId(dishId);
        } else {
            comments = commentMapper.queryBySetmealId(setmealId);
        }
        return comments;
    }
}
