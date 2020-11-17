package com.example.springboot.service;

import com.example.springboot.entity.User;

import java.util.List;

public interface IUserService {

    void insert(User user);

    User select(int id);

    void delete(int id);

    void update(User user);

    void batchInsert(List<User> users);
}
