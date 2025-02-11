package yun.backend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * @param <T>
 *
 * @author maoyunlong
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String msg;
    private String description;
    public BaseResponse(){};

    public BaseResponse(int code, T data, String msg ,String description) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.description = description;
    }
    public BaseResponse(int code, T data, String description) {
        this(code, data, "", description);
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMsg(), errorCode.getDescription());
    }

    public BaseResponse(ErrorCode errorCode, T data, String description) {
        this(errorCode.getCode(), data, description);
    }

    public BaseResponse(ErrorCode errorCode, String description) {
        this.code = errorCode.getCode();
        this.data = null;
        this.msg = errorCode.getMsg();
        this.description = description;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
