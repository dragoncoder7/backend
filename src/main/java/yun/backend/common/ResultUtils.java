package yun.backend.common;

/**
 * 返回工具类
 *
 * @author Maoyunlong
 */
public class ResultUtils {
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0, data, "success");
    }

    public static <T> BaseResponse<T> failure(int code, T data, String msg){
        return new BaseResponse<>(code, data, msg);
    }

    public static <T> BaseResponse<T> failure(ErrorCode errorCode){
        return new BaseResponse<>(errorCode.getCode(), null, errorCode.getMsg());
    }

    public static <T> BaseResponse<T> failure(ErrorCode errorCode, String msg){
        return new BaseResponse<>(errorCode, msg);
    }
    public static <T> BaseResponse<T> failure(ErrorCode errorCode,T data, String msg){
        return new BaseResponse<>(errorCode, data, msg);
    }

}
