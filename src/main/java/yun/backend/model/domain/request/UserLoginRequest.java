package yun.backend.model.domain.request;

import lombok.Data;

/**
 * 用户登录请求体
 *
 * @author Maoyunlong
 */
@Data
public class UserLoginRequest {
    String userAccount;

    String userPassword;
}
