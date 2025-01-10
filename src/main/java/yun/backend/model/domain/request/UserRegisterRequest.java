package yun.backend.model.domain.request;

import lombok.Data;

/**
 * 用户注册请求体
 *
 * @author Maoyunlong
 */
@Data
public class UserRegisterRequest {
    String userAccount;

    String userPassword;

    String checkPassword;
}
