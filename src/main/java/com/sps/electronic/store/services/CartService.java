package com.sps.electronic.store.services;

import com.sps.electronic.store.dtos.AddItemToCartRequest;
import com.sps.electronic.store.dtos.CartDto;

public interface CartService {

    //add item to the cart
    //case 1: cart for user is not available: we will create the cart and then add the item
    //case 2: if cart available then add the items to the cart

    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //remove item from cart:
    void removeItemFromCart(String cartId, int cartItem);

    //remove all item from cart
    void clearCart(String userId);

    CartDto getCartByUser(String userId);
}
