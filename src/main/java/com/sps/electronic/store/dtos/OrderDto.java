package com.sps.electronic.store.dtos;

import com.sps.electronic.store.enitities.OrderItem;
import com.sps.electronic.store.enitities.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private String orderId;

    //we make by default orderStatus is pending
    private String orderStatus="PENDING";

    //now our payment is also pending so by default it is NOTPAID
    private String paymentStatus="NOTPAID";

    private int orderAmount;

    private String billingAddress;

    private  String billingPhone;

    private String billingName;

    //orderedDate also by default
    private Date orderedDate= new Date();

    private Date deliveredDate;

    //if we want to send information of the user with order only then otherwise comment out
    //private UserDto userdto;

    private List<OrderItemDto> orderItems = new ArrayList<>();
}
