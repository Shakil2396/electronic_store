package com.sps.electronic.store.repositories;

import com.sps.electronic.store.enitities.Order;
import com.sps.electronic.store.enitities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUser(User user);
}
