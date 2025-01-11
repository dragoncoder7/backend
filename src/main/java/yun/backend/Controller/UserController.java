package yun.backend.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import yun.backend.mapper.UserMapper;
import yun.backend.model.domain.User;
import yun.backend.model.domain.request.UserLoginRequest;
import yun.backend.model.domain.request.UserRegisterRequest;
import yun.backend.service.UserService;
import yun.backend.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static yun.backend.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public long UserRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null) {
            return -1;
        }
        String username = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAllBlank(username, password, checkPassword)){
            return -1;
        }
        return userService.UserRegister(username, password, checkPassword);
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User UserLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        System.out.println("进入调用接口"+ new Date());
        if (userLoginRequest == null) {
            return null;
        }
        String username = userLoginRequest.getUserAccount();
        String password = userLoginRequest.getUserPassword();
        if (StringUtils.isAllBlank(username, password)){
            return null;
        }
        return userService.userLogin(username, password, request);
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public List<User> searchUsers(String username, HttpServletRequest request) {
        if (!isAdmin(request)){
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username",username);
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user -> userService.getSafeUser(user)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public boolean deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!isAdmin(request)){
            return false;
        }
        if (id < 0){
            return false;
        }
        return userService.removeById(id);
    }

    @RequestMapping(value = "/getCurrent", method = RequestMethod.GET)
    public User getCurrent(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null){
            return null;
        }
        user = userService.getById(user.getId());
        return userService.getSafeUser(user);
    }

    public boolean isAdmin(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
        return user != null && user.getUserRole() == 1;
    }
}
