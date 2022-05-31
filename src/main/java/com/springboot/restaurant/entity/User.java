package com.springboot.restaurant.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="enabled")
    private int enabled;



    public User() {}

    public User(String username, String password, int enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    @OneToMany(fetch= FetchType.LAZY, mappedBy="user",
            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Item> items;

    @OneToOne(fetch= FetchType.LAZY,
            cascade= {CascadeType.ALL})
    @JoinColumn(name="cart_id")
    private Cart cart;


}


