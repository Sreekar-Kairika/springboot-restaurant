package com.springboot.restaurant.service;

import com.springboot.restaurant.entity.Cart;

public interface CartService {

    public Cart findById(int id);

    public void save(Cart cart);

    public void deleteById(int id);
}
