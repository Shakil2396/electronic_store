package com.sps.electronic.store.services.impl;

import com.sps.electronic.store.dtos.UserDto;
import com.sps.electronic.store.enitities.User;
import com.sps.electronic.store.repositories.UserRepository;
import com.sps.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        //generate unique id in in string format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        //converting manually but latr we will learn mapper class concept
        //dto to entity
        User user = dtoToEntity(userDto);
        User userEntity = userRepository.save(user);

        //entity to dto
        UserDto dto = entityToDto(userEntity);
        return dto;
    }



    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found with this id"));

        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail()); //we can also update email here
        user.setAbout(userDto.getAbout());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setImage(userDto.getImage());

        //save data
        User userEntity = userRepository.save(user);

        UserDto userDto1 = entityToDto(userEntity);
        return userDto1;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found with this id"));

        //delete user
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        //here we use steam api to convert list of user to list of dto...we can also use for each
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found with this id"));
        UserDto userDto = entityToDto(user);
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user not found with this email"));
        UserDto userDto = entityToDto(user);
        return userDto;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> userList = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = userList.stream().map(users -> entityToDto(users)).collect(Collectors.toList());
        return dtoList;
    }

    private UserDto entityToDto(User userEntity) {
//        UserDto dto = UserDto.builder()
//                .userId(userEntity.getUserId())
//                .name(userEntity.getName())
//                .email(userEntity.getEmail())
//                .password(userEntity.getPassword())
//                .about(userEntity.getAbout())
//                .gender(userEntity.getAbout())
//                .image(userEntity.getImage())
//                .build();
//        return dto;

        return mapper.map(userEntity, UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
//        User user = new User();
//        or we use builder
//        User entity = User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .image(userDto.getImage())
//                .build();
//        return entity;

        return mapper.map(userDto, User.class);
    }

}
