package com.sps.electronic.store.controllers;

import com.sps.electronic.store.dtos.AddItemToCartRequest;
import com.sps.electronic.store.dtos.ApiResponseMessage;
import com.sps.electronic.store.dtos.CartDto;
import com.sps.electronic.store.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    //add items to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemtoCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request){

        //here we get userId form login user we will see this while learning spring security and
        //second we will get our user from the url here we get from the url
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    //remove item to the cart
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponseMessage> removeItrmFromCart(@PathVariable String userId, @PathVariable int itemId){
        cartService.removeItemFromCart(userId, itemId);

        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message("item is deleted sucessfully!!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    //clear cart
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId){
        cartService.clearCart(userId);

        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message("now cart is blank !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    //get cart
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId){

        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
}
