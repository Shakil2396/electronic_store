package com.sps.electronic.store.services;

import com.sps.electronic.store.dtos.CreateOrderRequest;
import com.sps.electronic.store.dtos.OrderDto;
import com.sps.electronic.store.dtos.PageableResponse;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface OrderService {

    //create order
    OrderDto createOrder(CreateOrderRequest orderDto);

    //remove order
    void removeOrder(String orderId);

    //get orders of user
    //we can also use pagination here if i want
    List<OrderDto> getOrdersOfUser(String userId);

    //get orders //for admin use
    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

    //other method(logic) related to the order
}
