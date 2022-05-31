package com.springboot.restaurant.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name="items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="item_name")
    private String itemName;


    @Column(name="price")
    private int price;


    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="username")
    private User user;

    public Item(){}

    public Item(String itemName, int price) {
        this.itemName = itemName;
        this.price = price;
    }

}
