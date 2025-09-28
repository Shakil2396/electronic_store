package com.sps.electronic.store.enitities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String orderId;

    //PENDING, DELEVERED, DISPATCHED ...this changes done by the admin
    //or use ENUM
    private String orderStatus;

    //PENDING/NOTPAID when order the product, then PAID
    //use ENUM or BOLLEAN- false means not paid || true means paid
    private String paymentStatus;

    private int orderAmount;

    @Column(length= 1000)
    private String billingAddress;

    private  String billingPhone;

    private String billingName;

    private Date orderedDate;

    //we can make one api for this this will updated by the delevery boy or admin
    private Date deliveredDate;

    //user
    //EAGER means when we fetch the order then we will also recieve user details
    @ManyToOne(fetch = FetchType.EAGER)   //one user have multiple orders
    @JoinColumn(name = "user_id")
    private User user;

    //cascadeAll means remove or save orders then at that time items also save or remove
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
}
