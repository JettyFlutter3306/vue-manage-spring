package cn.element.exception;

import cn.element.common.ResultInfo;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ArithmeticException.class)
    public ResultInfo arithmeticExceptionHandle(ArithmeticException e) {

        e.printStackTrace();

        String msg = "算术异常,请稍后再试!";

        return ResultInfo.serverError(msg);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResultInfo nullPointerException(NullPointerException e) {

        return ResultInfo.serverError(e.getMessage());
    }
}
