package com.meta.mall.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CategoryBatchUpdateReq {
    @NotNull(message = "ids shouldn't be null")
    @NotEmpty(message = "ids shouldn't be empty")
    private Integer[] ids;

    @NotNull(message = "need status")
    private Integer status;


    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
