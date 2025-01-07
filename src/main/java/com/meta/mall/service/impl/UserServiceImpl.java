package com.meta.mall.service.impl;

import com.meta.mall.exception.MallException;
import com.meta.mall.exception.MallExceptionEnum;
import com.meta.mall.model.pojo.User;
import com.meta.mall.model.repository.UserRepository;
import com.meta.mall.service.UserService;
import com.meta.mall.util.MD5Utils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser() {
        return userRepository.findById(2L).orElse(null);
    }

    @Override
    public void register(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new MallException(MallExceptionEnum.DUPLICATE_USERNAME);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Utils.getMD5String(password));

        userRepository.save(user);
    }
}
