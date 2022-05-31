package com.springboot.restaurant.entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="cart_item")
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @OneToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(cascade= {CascadeType.ALL})
    @JoinColumn(name="cart_id")
    private Cart cart;

    @Column(name="quantity")
    private int quantity;

    public CartItem() {
        quantity = 0;
    }

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

}
