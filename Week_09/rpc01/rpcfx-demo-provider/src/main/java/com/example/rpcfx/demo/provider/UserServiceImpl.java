package com.example.rpcfx.demo.provider;

import com.example.rpcfx.demo.api.User;
import com.example.rpcfx.demo.api.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User findById(int id) {
        return new User(id, "KK" + System.currentTimeMillis());
    }
}
