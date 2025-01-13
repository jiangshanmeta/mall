package com.meta.mall.controller;


import com.meta.mall.common.ApiRestResponse;
import com.meta.mall.common.Constant;
import com.meta.mall.exception.MallExceptionEnum;
import com.meta.mall.model.pojo.User;
import com.meta.mall.model.request.AdminLoginReq;
import com.meta.mall.model.request.LoginReq;
import com.meta.mall.model.request.RegisterReq;
import com.meta.mall.model.request.UpdateSignatureReq;
import com.meta.mall.model.response.UserVO;
import com.meta.mall.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/personal")
    public User personalPage() {
        return userService.getUser();
    }

    @PostMapping("/register")
    public ApiRestResponse<Void> register(@RequestBody RegisterReq registerReq) {
        if (StringUtils.isEmpty(registerReq.getUsername())) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(registerReq.getPassword())) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }

        if (registerReq.getPassword().length() < 8) {
            return ApiRestResponse.error(MallExceptionEnum.PASSWORD_TOO_SHORT);
        }

        userService.register(registerReq.getUsername(), registerReq.getPassword());

        return ApiRestResponse.success();
    }

    @PostMapping("/login")
    public ApiRestResponse<UserVO> login(@RequestBody LoginReq loginReq, HttpSession httpSession) {
        if (StringUtils.isEmpty(loginReq.getUsername())) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(loginReq.getPassword())) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }

        User user = userService.login(loginReq.getUsername(), loginReq.getPassword());

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        httpSession.setAttribute(Constant.MALL_USER, user);

        return ApiRestResponse.success(userVO);
    }

    @PostMapping("/update")
    public ApiRestResponse<Void> updateSignature(@RequestBody UpdateSignatureReq updateSignatureReq, HttpSession httpSession) {
        User currentUser = (User) httpSession.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);
        }
        currentUser.setPersonalizedSignature(updateSignatureReq.getSignature());


        userService.updateSignature(currentUser.getId(), updateSignatureReq.getSignature());

        return ApiRestResponse.success();
    }

    @PostMapping("/logout")
    public ApiRestResponse<Void> logout(HttpSession httpSession) {
        httpSession.removeAttribute(Constant.MALL_USER);
        return ApiRestResponse.success();
    }


    @PostMapping("/admin/login")
    public ApiRestResponse<User> adminLogin(@RequestBody AdminLoginReq adminLoginReq, HttpSession httpSession) {
        if (StringUtils.isEmpty(adminLoginReq.getUsername())) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(adminLoginReq.getPassword())) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }

        User user = userService.login(adminLoginReq.getUsername(), adminLoginReq.getPassword());
        if (!userService.isAdmin(user)) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_ADMIN);
        }

        user.setPassword(null);

        httpSession.setAttribute(Constant.MALL_USER, user);

        return ApiRestResponse.success(user);
    }


}
