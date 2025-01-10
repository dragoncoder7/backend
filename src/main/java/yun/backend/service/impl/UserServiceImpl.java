package yun.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import yun.backend.mapper.UserMapper;
import yun.backend.model.domain.User;
import yun.backend.service.UserService;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static yun.backend.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author Maoyunlong
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-01-06 20:04:00
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    private static final String salt = "long";;

    @Resource
    private UserMapper userMapper;

    /**
     * 用户注册
     *
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassword 二次确认密码
     * @return 用户id
     */
    @Override
    public long UserRegister(String userAccount, String userPassword, String checkPassword) {
        //判断数据是否为空
        //todo 记得修改为自定义异常
        if (StringUtils.isAllBlank(userAccount, userPassword, checkPassword)){
            return -1;
        }
        //要求账号长度至少超过5位， 密码长度超过8位
        if (userAccount.length() < 5 || userPassword.length() < 8 || checkPassword.length() < 8){
            return -1;
        }
        //确保密码和二次确认密码一致
        if (!userPassword.equals(checkPassword)){
            return -1;
        }
        // 密码正则表达式，至少包含字母+数字、字母+特殊字符或数字+特殊字符
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)|(?=.*[A-Za-z])(?=.*[@$!%*?&])|(?=.*\\d)(?=.*[@$!%*?&])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userPassword);
        if (!matcher.find()){
            return -1;
        }
        //账号只能包含数字和字母
        regex = "^[A-Za-z0-9]+$";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(userAccount);
        if (!matcher.find()){
            return -1;
        }
        //效验账号是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        if (userMapper.selectCount(queryWrapper) > 0){
            return -1;
        }
        //插入用户
        User user = new User();
        //密码MD5加密
        // 使用 Apache Commons Codec 的 DigestUtils 进行MD5加密
        String encryptPassword = DigestUtils.md5DigestAsHex((salt+userPassword).getBytes());
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean res = this.save(user);
        if (!res){
            return -1;
        }
        return user.getId();
    }

    /**
     * 用户登录
     *
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @return 用户
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request){
        //判断数据是否为空
        //todo 记得修改为自定义异常
        if (StringUtils.isAllBlank(userAccount, userPassword)){
            return null;
        }
        //要求账号长度至少超过5位， 密码长度超过8位
        if (userAccount.length() < 5 || userPassword.length() < 8){
            return null;
        }
        //效验账号是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String encryptPassword = DigestUtils.md5DigestAsHex((salt+userPassword).getBytes());
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);

        if (user == null){
            log.info("login failed! username and password is not same!");
            return null;
        }
        User SafeUser = getSafeUser(user);
        request.getSession().setAttribute(USER_LOGIN_STATE,SafeUser);
        return SafeUser;
    }

    @Override
    public User getSafeUser(User user) {
        User safyuser = new User();
        safyuser.setId(user.getId());
        safyuser.setUsername(user.getUsername());
        safyuser.setUserAccount(user.getUserAccount());
        safyuser.setAvatarUrl(user.getAvatarUrl());
        safyuser.setGender(user.getGender());
        safyuser.setPhone(user.getPhone());
        safyuser.setEmail(user.getEmail());
        safyuser.setUserStatus(user.getUserStatus());
        safyuser.setCreateTime(new Date());
        safyuser.setUserRole(user.getUserRole());
        safyuser.setPlanetCode(user.getPlanetCode());
        return safyuser;
    }
}




