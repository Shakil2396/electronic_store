package com.sps.electronic.store.services.impl;

import com.sps.electronic.store.dtos.CreateOrderRequest;
import com.sps.electronic.store.dtos.OrderDto;
import com.sps.electronic.store.dtos.PageableResponse;
import com.sps.electronic.store.enitities.*;
import com.sps.electronic.store.exceptions.BadApiRequest;
import com.sps.electronic.store.exceptions.ResourceNotFoundException;
import com.sps.electronic.store.helpers.Helper;
import com.sps.electronic.store.repositories.CartRepository;
import com.sps.electronic.store.repositories.OrderRepository;
import com.sps.electronic.store.repositories.UserRepository;
import com.sps.electronic.store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {
        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();

        //fetch user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with given id"));

        //fetch cart using cart id
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("cart with given id not found on server!!"));

        //now i want to convert cart item into the order item
        //first of all get all the items from the cart
        List<CartItem> cartItems = cart.getItems();

        if (cartItems.size()<=0){
            throw new BadApiRequest("Invalid number of items in cart!!");
        }

        //other checks
        Order order = Order.builder()
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderedDate(new Date())
                .deliveredDate(null) //by default null
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())//to generate random order id
                .user(user)  //to set user order kis user ka he
                .build();

        //note orderItems and amount are not set
        //now change cart item to order item
        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);

        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            //cartItem -> orderItem
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();

            //set order amount here
            orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());

            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        //now clear the cart
        cart.getItems().clear();
        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);

        return modelMapper.map(savedOrder,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order is not found"));
        orderRepository.delete(order); //now when we delete order then items also be deleted because cascade all in order
    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        //now we have to find orders of this given userId
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with this given id"));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());

        return orderDtos;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Order> page = orderRepository.findAll(pageable);

        return Helper.getPageableResponse(page, OrderDto.class);
    }
}
