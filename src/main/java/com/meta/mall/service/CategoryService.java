package com.meta.mall.service;

import com.meta.mall.model.request.AddCategoryReq;
import com.meta.mall.model.request.UpdateCategoryReq;

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    void update(UpdateCategoryReq updateCategoryReq);
}
