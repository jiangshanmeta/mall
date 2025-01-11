package com.meta.mall.service.impl;

import com.meta.mall.exception.MallException;
import com.meta.mall.exception.MallExceptionEnum;
import com.meta.mall.model.pojo.Category;
import com.meta.mall.model.request.AddCategoryReq;
import com.meta.mall.model.request.UpdateCategoryReq;
import com.meta.mall.repository.CategoryRepository;
import com.meta.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public void update(UpdateCategoryReq updateCategoryReq) {
        if (updateCategoryReq.getName() != null) {
            List<Category> categoryList = categoryRepository.findAllByName(updateCategoryReq.getName());
            boolean duplicate = categoryList.stream().anyMatch((category -> {
                return !category.getId().equals(updateCategoryReq.getId()) && category.getName().equals(updateCategoryReq.getName());
            }));
            if (duplicate) {
                throw new MallException(MallExceptionEnum.CATEGORY_NAME_EXIST);
            }
        }

        Category category = categoryRepository.findById(updateCategoryReq.getId()).orElseThrow(() -> new MallException(MallExceptionEnum.CATEGORY_NOT_EXIST));

        if (updateCategoryReq.getName() != null) {
            category.setName(updateCategoryReq.getName());
        }
        if (updateCategoryReq.getType() != null) {
            category.setType(updateCategoryReq.getType());
        }
        if (updateCategoryReq.getOrderNum() != null) {
            category.setOrderNum(updateCategoryReq.getOrderNum());
        }
        if (updateCategoryReq.getParentId() != null) {
            category.setParentId(updateCategoryReq.getParentId());
        }

        categoryRepository.save(category);
    }

    @Override
    public void delete(Integer id) {
        boolean exist = categoryRepository.existsById(id);
        if (exist) {
            throw new MallException(MallExceptionEnum.CATEGORY_DELETE_FAIL);
        }
        categoryRepository.deleteById(id);

    }

    @Override
    public Page<Category> list(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }


}
