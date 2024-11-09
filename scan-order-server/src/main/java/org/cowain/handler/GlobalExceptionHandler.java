package org.cowain.handler;

import lombok.extern.slf4j.Slf4j;
import org.cowain.constant.MessageConstant;
import org.cowain.exception.BaseException;
import org.cowain.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 驳货业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getMessage());
    }

    /**
     * 处理SQL异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException e) {
        //Duplicate entry 'zhangsan' for key 'employee.idx_username'
        String message = e.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
