package com.meta.mall.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddCategoryReq {
    @Size(min = 2, max = 50, message = "name length should between 2 and 50")
    @NotNull(message = "category name should not be null")
    private String name;

    @NotNull(message = "type is required")
    @Max(value = 3, message = "type max value is 3")
    private Integer type;

    @NotNull(message = "parentId is required")
    private Integer parentId;

    @NotNull(message = "orderNum is required")
    private Integer orderNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "AddCategoryReq{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", parentId=" + parentId +
                ", orderNum=" + orderNum +
                '}';
    }
}
