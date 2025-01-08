package com.meta.mall.service.impl;

import com.meta.mall.exception.MallException;
import com.meta.mall.exception.MallExceptionEnum;
import com.meta.mall.model.pojo.User;
import com.meta.mall.repository.UserRepository;
import com.meta.mall.service.UserService;
import com.meta.mall.util.MD5Utils;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        user.setRole(1);

        userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        String md5Password = MD5Utils.getMD5String(password);

        Optional<User> userOptional = userRepository.findByUsernameAndPassword(username, md5Password);
        if (userOptional.isEmpty()) {
            throw new MallException(MallExceptionEnum.WRONG_PASSWORD);
        }

        return userOptional.get();
    }

    @Override
    public void updateSignature(Integer id, String signature) {
        boolean success = userRepository.updateSignature(id, signature);
        if (!success) {
            throw new MallException(MallExceptionEnum.UPDATE_SIGNATURE_FAIL);
        }
    }

    @Override
    public boolean isAdmin(User user) {
        return user.getRole() == 2;
    }


}
