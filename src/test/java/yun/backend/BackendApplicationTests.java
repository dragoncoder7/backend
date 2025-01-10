package yun.backend;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import yun.backend.model.domain.User;
import yun.backend.service.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class BackendApplicationTests {

    @Resource
    private UserService userService;
    @Test
    void test(){
        String username = "@1234651";
        String regex = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        if (!matcher.find()){
            System.out.println("不满足条件！");
        }
    }
    @Test
    void contextLoads() {
        User user = new User();
        user.setUserAccount("");
        String username = "";
        String password = "123";
        String checkPassword = "123";

        Assertions.assertEquals(-1,userService.UserRegister(username, password, checkPassword));
        username = "maoyunlong";
        password = "mao7160805";
        checkPassword = password;
        Assertions.assertEquals(-1,userService.UserRegister(username, password, checkPassword));

        password = "123";
        checkPassword = password;
        Assertions.assertEquals(-1,userService.UserRegister(username, password, checkPassword));
        password = "12345678";
        checkPassword = password;
        Assertions.assertEquals(-1,userService.UserRegister(username, password, checkPassword));

        username = "1234";
        Assertions.assertEquals(-1,userService.UserRegister(username, password, checkPassword));

        username = "along1";
        checkPassword = username;
        Assertions.assertEquals(-1,userService.UserRegister(username, password, checkPassword));

        username = "{#123444";
        Assertions.assertEquals(-1,userService.UserRegister(username, password, checkPassword));


    }

}
