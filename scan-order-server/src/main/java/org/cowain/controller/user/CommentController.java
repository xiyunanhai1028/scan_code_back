package org.cowain.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cowain.dto.CommentDTO;
import org.cowain.entity.Comment;
import org.cowain.result.Result;
import org.cowain.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "评论相关接口")
@RestController("userCommentController")
@RequestMapping("/user/comment")
@Slf4j
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ApiOperation("提交评论")
    @PostMapping
    public Result<String> save(@RequestBody CommentDTO comment) {
        commentService.save(comment);
        return Result.success("保存成功");
    }

    @ApiOperation("查询评论")
    @GetMapping
    public Result<List<Comment>> list(Long dishId, Long setmealId) {
        log.info("查询评论：{},{}", dishId, setmealId);
        List<Comment> list = commentService.listByDishIdOrSetmealId(dishId, setmealId);
        return Result.success(list);
    }
}
