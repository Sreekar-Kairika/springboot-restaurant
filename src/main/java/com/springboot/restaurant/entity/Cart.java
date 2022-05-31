package com.springboot.restaurant.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="cart")
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="total")
    private double total;

    @Column(name="total_items")
    private int totalItems;

    public Cart() {
        total=0;
        totalItems=0;
    }
    public Cart(User user, List<CartItem> cartItems, double total, int totalItems) {
        this.user = user;
        this.total = total;
        this.totalItems = totalItems;
        this.cartItems = cartItems;

    }
    @OneToOne(fetch= FetchType.LAZY, mappedBy = "cart" ,cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    private User user;

    @OneToMany(fetch= FetchType.LAZY, mappedBy="cart",
            cascade= {CascadeType.ALL})
    private List<CartItem> cartItems;
}
