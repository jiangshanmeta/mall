package com.meta.mall.controller;

import com.meta.mall.common.ApiRestResponse;
import com.meta.mall.common.Constant;
import com.meta.mall.exception.MallExceptionEnum;
import com.meta.mall.model.pojo.User;
import com.meta.mall.model.request.AddCategoryReq;
import com.meta.mall.service.CategoryService;
import com.meta.mall.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final UserService userService;
    private final CategoryService categoryService;

    public CategoryController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @PostMapping("/admin/add")
    public ApiRestResponse<Void> addCategory(HttpSession session, @RequestBody AddCategoryReq addCategoryReq) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<AddCategoryReq>> violations = validator.validate(addCategoryReq);
        if (!violations.isEmpty()) {
            String msg = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
            return ApiRestResponse.error(20000, msg);
        }


        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);
        }
        if (!userService.isAdmin(currentUser)) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_ADMIN);
        }

        categoryService.add(addCategoryReq);

        return ApiRestResponse.success();
    }
}
