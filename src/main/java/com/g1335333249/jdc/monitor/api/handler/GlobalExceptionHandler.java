package com.g1335333249.jdc.monitor.api.handler;

import com.g1335333249.jdc.monitor.api.exception.CustomizeException;
import com.g1335333249.jdc.monitor.api.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guanpeng
 * @description 全局异常拦截
 * @date 2020/4/26 10:34 上午
 * @since
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception Handler.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleConstraintViolationException(ConstraintViolationException e) {
        log.info("参数错误");
        return Result.fail(HttpStatus.BAD_REQUEST.value(), "参数错误: " + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> exception(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        List<String> errorMsg = new ArrayList<>();

        for (FieldError error : fieldErrors) {
            errorMsg.add(error.getDefaultMessage());
        }
        return Result.fail(String.join(",", errorMsg));
    }

    /**
     * 自定义Exception处理.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(CustomizeException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> hffssException(CustomizeException e) {
        return Result.fail(e.getMessage());
    }

    /**
     * Exception Handler.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> exception(Exception e) {
        return Result.fail(HttpStatus.BAD_REQUEST.value(), "系统错误: " + e.getMessage());
    }
}
