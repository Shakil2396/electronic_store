package com.sps.electronic.store.repositories;

import com.sps.electronic.store.enitities.Category;
import com.sps.electronic.store.enitities.Product;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.hibernate.boot.archive.scan.spi.PackageInfoArchiveEntryHandler;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    //search
    Page<Product> findByTitleContaining(String sabTitle, Pageable pageable);

    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);
    //other methods
    //custom finder methods
    //query methods
}
