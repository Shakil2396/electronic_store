package com.sps.electronic.store.repositories;

import com.sps.electronic.store.enitities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
