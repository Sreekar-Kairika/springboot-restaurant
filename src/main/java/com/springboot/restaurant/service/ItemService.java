package com.springboot.restaurant.service;



import com.springboot.restaurant.entity.Item;

import java.util.List;

public interface ItemService {

    public List<Item> findAll();

    public Item findById(int id);

    public void save(Item item);

    public void deleteById(int id);
}
