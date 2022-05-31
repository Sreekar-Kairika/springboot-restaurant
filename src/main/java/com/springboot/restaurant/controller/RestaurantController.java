package com.springboot.restaurant.controller;

import com.springboot.restaurant.entity.*;
import com.springboot.restaurant.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;

@Controller
public class RestaurantController {


    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;

    public RestaurantController() {}

    @GetMapping("/")
    public String getAllBooks(Model theModel){
        List<Item> items = itemService.findAll();
        theModel.addAttribute("items", items);
        return "menu";
    }




    @GetMapping("/showAddForm")
    public String showFormAdd(Model theModel) {
        Item item = new Item();
        theModel.addAttribute(item);
        return "item-form";
    }

    @GetMapping("/showFormUpdate")
    public String showFormUpdate(@RequestParam("itemId") int iId, Model theModel) {
        Item item = itemService.findById(iId);
        theModel.addAttribute("item", item);
        return "item-form";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("item") @Valid Item theItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "item-form";
        }
        User user = userService.getCurrentUser();
        theItem.setUser(user);
        user.getItems().add(theItem);
        itemService.save(theItem);
        return "redirect:/manager-items";
    }

    @GetMapping("/manager-items")
    public String getManagerItems(Model theModel) {
        User user = userService.getCurrentUser();
        if(user==null)
        {
            return "access-denied";
        }
        theModel.addAttribute("managerItems", user.getItems());
        return "manager-home";
    }

    @GetMapping("/deleteItem")
    public String deleteProduct(@RequestParam("itemId") int dId) {
        itemService.deleteById(dId);
        return "redirect:/manager-items";
    }



    @GetMapping("/getCart")
    public String getCart(Model theModel) {

        User user = userService.getCurrentUser();
        if(user==null)
        {
            return "access-denied";
        }
        Cart cart = user.getCart();
        if (cart == null || cart.getTotal() == 0) {
            theModel.addAttribute("noItem", true);
        } else {
            theModel.addAttribute("cartItems", cart.getCartItems());
            theModel.addAttribute("total", cart.getTotal());
        }
        return "cart";
    }

    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("itemId") int iId) {
        Item item = itemService.findById(iId);
        User user = userService.getCurrentUser();
        if(item.getUser().equals(user))
        {
            return "redirect:/";
        }
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }
        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        boolean found = false;
        double total = cart.getTotal();
        for (CartItem cartItem : cartItems) {
            if (cartItem.getItem().equals(item)) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cart.setTotalItems(cart.getTotalItems()+1);
                found = true;
                break;
            }
        }
        if (!found) {
            CartItem newItem = new CartItem(item, 1);
            cart.setTotalItems(cart.getTotalItems()+1);
            newItem.setCart(cart);
            cartItems.add(newItem);
        }
        total += item.getPrice();
        cart.setTotal(total);
        cart.setCartItems(cartItems);
        user.setCart(cart);
        cartService.save(cart);
        return "redirect:/";
    }

    @GetMapping("/checkout")
    public String checkout() {
        User user = userService.getCurrentUser();
        Cart cart = user.getCart();
        user.setCart(null);
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("decreaseInCart")
    public String decreaseInCart(@RequestParam("itemId") int iId) {
        Item item = itemService.findById(iId);
        User user = userService.getCurrentUser();
        Cart cart = user.getCart();
        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem cartItem : cartItems) {
            if (cartItem.getItem().equals(item)) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cart.setTotalItems(cart.getTotalItems()-1);
                if (cartItem.getQuantity() == 0) {
                    cartItems.remove(cartItem);
                    cartItemService.deleteById(cartItem.getId());
                }
                break;
            }

        }

        double total = cart.getTotal();
        total -= item.getPrice();
        cart.setTotal(total);
        cart.setCartItems(cartItems);
        user.setCart(cart);
        userService.save(user);
        return "redirect:/getCart";
    }

    @GetMapping("/deleteCartItem")
    public String deleteCartItem(@RequestParam("itemId") int iId) {
        Item item = itemService.findById(iId);

        User user = userService.getCurrentUser();

        Cart cart = user.getCart();

        List<CartItem> cartItems = cart.getCartItems();

        int qty = 0;

        for (CartItem cartItem : cartItems) {
            if (cartItem.getItem().equals(item)) {
                qty = cartItem.getQuantity();
                cartItems.remove(cartItem);
                cartItemService.deleteById(cartItem.getId());
                break;
            }

        }

        double total = cart.getTotal();
        total -= item.getPrice() * qty;

        cart.setTotal(total);
        cart.setCartItems(cartItems);
        user.setCart(cart);
        userService.save(user);

        return "redirect:/getCart";

    }

}
