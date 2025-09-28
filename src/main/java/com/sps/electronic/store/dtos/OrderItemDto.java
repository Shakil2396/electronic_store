package com.sps.electronic.store.dtos;

import com.sps.electronic.store.enitities.Order;
import com.sps.electronic.store.enitities.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemDto {

    private int orderItemId;

    private int quantity;

    private int totalPrice;

    private ProductDto product;

    //i dont want to send order information inside item so comment out
//    private Order order;
}
