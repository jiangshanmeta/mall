package com.meta.mall.service.impl;

import com.meta.mall.exception.MallException;
import com.meta.mall.exception.MallExceptionEnum;
import com.meta.mall.model.pojo.Category;
import com.meta.mall.model.request.AddCategoryReq;
import com.meta.mall.repository.CategoryRepository;
import com.meta.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq, category);
        if (categoryRepository.existsByName(category.getName())) {
            throw new MallException(MallExceptionEnum.CATEGORY_NAME_EXIST);
        }


        categoryRepository.save(category);

    }

}
