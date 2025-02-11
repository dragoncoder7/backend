package yun.backend.exception;

import lombok.Getter;
import yun.backend.common.ErrorCode;

import java.io.Serializable;

@Getter
public class CustomException extends RuntimeException implements Serializable {
    private final int code;

    private final String description;

    public CustomException(int code,  String description){
        this.code = code;
        this.description = description;
    }

    public CustomException(int code, String message, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public CustomException(ErrorCode errorCode, String description) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

}
