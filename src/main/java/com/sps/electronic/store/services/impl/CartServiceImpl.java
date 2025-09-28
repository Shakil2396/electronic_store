package com.sps.electronic.store.services.impl;

import com.sps.electronic.store.dtos.AddItemToCartRequest;
import com.sps.electronic.store.dtos.CartDto;
import com.sps.electronic.store.enitities.Cart;
import com.sps.electronic.store.enitities.CartItem;
import com.sps.electronic.store.enitities.Product;
import com.sps.electronic.store.enitities.User;
import com.sps.electronic.store.exceptions.BadApiRequest;
import com.sps.electronic.store.exceptions.ResourceNotFoundException;
import com.sps.electronic.store.repositories.CartItemRepository;
import com.sps.electronic.store.repositories.CartRepository;
import com.sps.electronic.store.repositories.ProductRepository;
import com.sps.electronic.store.repositories.UserRepository;
import com.sps.electronic.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if (quantity <= 0){
            throw new BadApiRequest("Requested quantity is not valid !!!");
        }

        //fetch the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found in database!!"));

        //fetch the user from db
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found in the db!!"));

//        Cart cart = cartRepository.findByUser(user).get();//optional cart is required in cartRepository to use this get method
        //if cart is not there then NoSuchElementException occur
        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart(); //if cart is not available then new cart created here
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date()); //when cart is created then this date added automatically
        }

        //perform cart operations
        //if cart items already present then update
        //boolean updated = false; //not working inside steam api so we have to try another approach
        AtomicReference<Boolean> updated = new AtomicReference<>(false);

        List<CartItem> items = cart.getItems();
        items = items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
                //item already present in the cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getDiscountedPrice());

                //updated = true; //not working inside stream api so
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

//        cart.setItems(updatedItems);

        //create items
        if (!updated.get()) {
            //CartItem cartItem = CartItem.builder()  //if this not working then
            CartItem cartItem = CartItem.builder()  //if we using manually builder pattern in Cart item
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();

            cart.getItems().add(cartItem);
        }
            //if user are new then
            cart.setUser(user);
            Cart updatedCart = cartRepository.save(cart); //we used cascade all so when card is updated then item also updated
            return mapper.map(updatedCart, CartDto.class);
    }


    @Override
    public void removeItemFromCart(String cartId, int cartItem) {

        //if you want to perform validation write conditions here

        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("Cart item not found !!!"));
        cartItemRepository.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) {

        //fetch the user from db
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("uset is not found with this id!!!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found!!!"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("uset is not found with this id!!!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found!!!"));

        return mapper.map(cart, CartDto.class);
    }
}
