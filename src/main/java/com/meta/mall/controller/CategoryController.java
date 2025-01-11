package com.meta.mall.controller;

import com.meta.mall.common.ApiRestResponse;
import com.meta.mall.model.pojo.Category;
import com.meta.mall.model.request.AddCategoryReq;
import com.meta.mall.model.request.UpdateCategoryReq;
import com.meta.mall.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
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


        categoryService.add(addCategoryReq);

        return ApiRestResponse.success();
    }

    @PostMapping("/admin/update")
    public ApiRestResponse<Void> updateCategory(HttpSession session, @RequestBody UpdateCategoryReq updateCategoryReq) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<UpdateCategoryReq>> violations = validator.validate(updateCategoryReq);
        if (!violations.isEmpty()) {
            String msg = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
            return ApiRestResponse.error(20000, msg);
        }


        categoryService.update(updateCategoryReq);

        return ApiRestResponse.success();
    }

    @DeleteMapping("/admin/delete/{id}")
    public ApiRestResponse<Void> deleteCategory(@PathVariable Integer id) {
        categoryService.delete(id);
        return ApiRestResponse.success();
    }

    @GetMapping("/list")
    public ApiRestResponse<Page<Category>> categoryList(Pageable pageable) {
        return ApiRestResponse.success(categoryService.list(pageable));
    }


}
