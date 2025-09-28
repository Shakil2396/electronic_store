package com.sps.electronic.store.repositories;

import com.sps.electronic.store.enitities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {


}
