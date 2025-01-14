package com.sps.electronic.store.repositories;

import com.sps.electronic.store.enitities.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

//    Optional<User> findByEmailAndPassword(String email, String password); //both match then object fetch

    List<User> findByNameContaining(String keywords);
}
