package yun.backend.model.domain.request;

import lombok.Data;

/**
 * 用户注册请求体
 *
 * @author Maoyunlong
 */
@Data
public class UserRegisterRequest {
    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String planetCode;
}
