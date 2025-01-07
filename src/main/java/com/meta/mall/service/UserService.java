package com.meta.mall.service;

import com.meta.mall.model.pojo.User;

public interface UserService {
    User getUser();

    void register(String username, String password);

}
