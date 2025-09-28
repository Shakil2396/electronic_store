package com.sps.electronic.store.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {

    @NotBlank(message = "Cart id is required!!")
    private String cartId;

    @NotBlank(message = "user id is required!!")
    private String userId;

    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
//    private int orderAmount; this is we are calculating from the user

    @NotBlank(message = "Address is required!!")
    private String billingAddress;

    @NotBlank(message = "phone number is required!!")
    private  String billingPhone;

    @NotBlank(message = "billing name  is required!!")
    private String billingName;
//    private Date orderedDate= new Date(); //in service
//    private Date deliveredDate;   //by default null hojayegi

//    private List<OrderItemDto> orderItems = new ArrayList<>();
}
