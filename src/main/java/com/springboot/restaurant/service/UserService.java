package com.springboot.restaurant.service;

import com.springboot.restaurant.entity.User;


public interface UserService {

    public User findById(String id);

    public void save(User user);

    public User getCurrentUser();

//    public User getByUsername();
}
