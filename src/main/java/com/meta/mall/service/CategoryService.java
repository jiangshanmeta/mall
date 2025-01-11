package com.meta.mall.service;

import com.meta.mall.model.pojo.Category;
import com.meta.mall.model.request.AddCategoryReq;
import com.meta.mall.model.request.UpdateCategoryReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    void update(UpdateCategoryReq updateCategoryReq);

    void delete(Integer id);

    Page<Category> list(Pageable pageable);
}
