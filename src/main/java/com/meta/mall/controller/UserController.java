package com.meta.mall.controller;


import com.meta.mall.common.ApiRestResponse;
import com.meta.mall.common.Constant;
import com.meta.mall.exception.MallExceptionEnum;
import com.meta.mall.model.pojo.User;
import com.meta.mall.model.response.UserVO;
import com.meta.mall.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/personal")
    public User personalPage() {
        return userService.getUser();
    }

    @PostMapping("/register")
    public ApiRestResponse<Void> register(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }

        if (password.length() < 8) {
            return ApiRestResponse.error(MallExceptionEnum.PASSWORD_TOO_SHORT);
        }

        userService.register(username, password);

        return ApiRestResponse.success();
    }

    @PostMapping("/login")
    public ApiRestResponse<UserVO> login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession httpSession) {
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }

        User user = userService.login(username, password);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        httpSession.setAttribute(Constant.MALL_USER, user);

        return ApiRestResponse.success(userVO);
    }

    @PostMapping("/update")
    public ApiRestResponse<Void> updateSignature(@RequestParam("signature") String signature, HttpSession httpSession) {
        User currentUser = (User) httpSession.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);
        }
        currentUser.setPersonalizedSignature(signature);


        userService.updateSignature(currentUser.getId(), signature);

        return ApiRestResponse.success();
    }

    @PostMapping("/logout")
    public ApiRestResponse<Void> logout(HttpSession httpSession) {
        httpSession.removeAttribute(Constant.MALL_USER);
        return ApiRestResponse.success();
    }


    @PostMapping("/admin/login")
    public ApiRestResponse<User> adminLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession httpSession) {
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }

        User user = userService.login(username, password);
        if (!userService.isAdmin(user)) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_ADMIN);
        }

        user.setPassword(null);

        httpSession.setAttribute(Constant.MALL_USER, user);

        return ApiRestResponse.success(user);
    }


}
