package yun.backend.service;

import jakarta.servlet.http.HttpServletRequest;
import yun.backend.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Maoyunlong
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-01-06 20:04:00
*/
public interface UserService extends IService<User> {
    long UserRegister(String username, String password, String checkPassword);

    long UserRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    User getSafeUser(User user);
}
