package com.meta.mall.service;

import com.meta.mall.model.pojo.User;

public interface UserService {
    User getUser();

    void register(String username, String password);

    User login(String username, String password);


    void updateSignature(Integer id, String signature);

    boolean isAdmin(User user);
}
