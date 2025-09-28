package com.sps.electronic.store.repositories;

import com.sps.electronic.store.enitities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
