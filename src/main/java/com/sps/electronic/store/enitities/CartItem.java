package com.sps.electronic.store.enitities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_items")
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private int totalPrice;

    //mapping cart
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

//
//    //default constructor
//    public CartItem() {
//    }
//
////getters and setters
//
//    public int getCartItemId() {
//        return cartItemId;
//    }
//
//    public void setCartItemId(int cartItemId) {
//        this.cartItemId = cartItemId;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public int getTotalPrice() {
//        return totalPrice;
//    }
//
//    public void setTotalPrice(int totalPrice) {
//        this.totalPrice = totalPrice;
//    }
//
//    public Cart getCart() {
//        return cart;
//    }
//
//    public void setCart(Cart cart) {
//        this.cart = cart;
//    }
//
//    // Private constructor to enforce object creation via Builder
//    private CartItem(Builder builder) {
//        this.cartItemId = builder.cartItemId;
//        this.product = builder.product;
//        this.quantity = builder.quantity;
//        this.totalPrice = builder.totalPrice;
//        this.cart = builder.cart;
//    }
//
//    // Static nested Builder class
//    public static class Builder {
//        private int cartItemId;
//        private Product product;
//        private int quantity;
//        private int totalPrice;
//        private Cart cart;
//
//        public Builder cartItemId(int cartItemId) {
//            this.cartItemId = cartItemId;
//            return this;
//        }
//
//        public Builder product(Product product) {
//            this.product = product;
//            return this;
//        }
//
//        public Builder quantity(int quantity) {
//            this.quantity = quantity;
//            return this;
//        }
//
//        public Builder totalPrice(int totalPrice) {
//            this.totalPrice = totalPrice;
//            return this;
//        }
//
//        public Builder cart(Cart cart) {
//            this.cart = cart;
//            return this;
//        }
//
//        public CartItem build() {
//            return new CartItem(this);
//        }
//    }

}
