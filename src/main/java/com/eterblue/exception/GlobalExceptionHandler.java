package com.eterblue.exception;

import com.eterblue.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public BaseResponse exceptionHandler(BaseException ex){
        log.error("异常信息:{}",ex.getMessage());
        return BaseResponse.error(ex.getMessage());
    }
    @ExceptionHandler
    public BaseResponse exceptionHandler(SQLIntegrityConstraintViolationException ex){
        String message = ex.getMessage();
        String[] username = message.split(" ");
        if(message.contains("Duplicate")){
            String msg=username[2]+"已存在";
            return BaseResponse.error(msg);
        }
        return BaseResponse.error("未知错误");
    }
    @ExceptionHandler(value = { MethodArgumentNotValidException.class})
    public BaseResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("------->MethodArgumentNotValidException参数异常-------- ", e);
        return BaseResponse.error(e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
