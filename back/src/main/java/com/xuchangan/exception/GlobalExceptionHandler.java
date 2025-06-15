package com.xuchangan.exception;

import com.xuchangan.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 这个类处理系统中未被捕获的异常，确保统一返回格式、提升用户体验与代码可维护性
// 主要用于解决validation带来的问题
// 不是用这个类，当出错时，会在后端控制台输出错误，我希望的是他也返回一个Result格式的错误

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
