package com.li.oauth.auth.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {
    //404错误占时获取不到，要加配置
    @ExceptionHandler(InsertUserException.class)
    public BaseResponse illegalArgumentException(InsertUserException ex) {
        log.error("获取参数错误：{}", ex.getMessage(), ex);
        return BaseResponse.Ret(ex.getCode(), ex.getMsg());
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException ex) {
        log.error("运行时异常：{}", ex.getMessage(), ex);
        return BaseResponse.Ret(500, "内部服务器错误");
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public BaseResponse nullPointerExceptionHandler(NullPointerException ex) {
        log.error("空指针异常：{} ", ex.getMessage(), ex);
        return BaseResponse.Ret(1001, null);
    }


    @ExceptionHandler(AuthenticationServiceException.class)
    public BaseResponse authenticationServiceExceptionHandler(NullPointerException ex) {
        log.error("空指针异常：{} ", ex.getMessage(), ex);
        return BaseResponse.Ret(1002, ex.getMessage());
    }


}
