package com.sps.electronic.store.services;

import com.sps.electronic.store.dtos.UserDto;
import com.sps.electronic.store.enitities.User;

import java.util.List;

public interface UserService {

    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto, String userId);

    //delete
    void deleteUser(String userId);

    //get all user
    List<UserDto> getAllUsers();

    //get single user by id
    UserDto getUserById(String userId);

    //get single user bu email
    UserDto getUserByEmail(String email);

    //search user
    List<UserDto> searchUser(String keyword);

    //other user specific features
}
