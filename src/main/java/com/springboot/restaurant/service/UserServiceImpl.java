package com.springboot.restaurant.service;


import com.springboot.restaurant.dao.UserRepository;
import com.springboot.restaurant.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService{
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository theUserRepository) {
        userRepository = theUserRepository;
    }

    @Override
    public User findById(String username) {
        Optional<User> result =  userRepository.findById(username);
        User user = null;
        if(result.isPresent())
        {
            user = result.get();
        }
        return user;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        User user;
        String username = principal.getName();
        user = findById(username);
        return user;
    }
}
