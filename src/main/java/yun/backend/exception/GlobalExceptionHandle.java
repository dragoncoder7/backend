package yun.backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import yun.backend.common.BaseResponse;
import yun.backend.common.ErrorCode;
import yun.backend.common.ResultUtils;

/**
 * @author Maoyunlong
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {
    @ExceptionHandler(CustomException.class)
    public BaseResponse<?> CustomExceptionHandle(CustomException e){
        log.error("CustomException: " + e.getMessage(), e);
        return ResultUtils.failure(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> RuntimeExceptionHandle(RuntimeException e){
        log.error("RuntimeException: ", e);
        return ResultUtils.failure(ErrorCode.SYSTEM_ERROR,e.getMessage());
    }
}
