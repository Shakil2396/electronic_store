package com.sps.electronic.store.services.impl;

import com.sps.electronic.store.dtos.PageableResponse;
import com.sps.electronic.store.dtos.UserDto;
import com.sps.electronic.store.enitities.Role;
import com.sps.electronic.store.enitities.User;
import com.sps.electronic.store.exceptions.ResourceNotFoundException;
import com.sps.electronic.store.helpers.Helper;
import com.sps.electronic.store.repositories.RoleRepository;
import com.sps.electronic.store.repositories.UserRepository;
import com.sps.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Value("${normal.role.id}")
    private String normalRoleId;

    @Autowired
    private RoleRepository roleRepository;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${user.profile.image.path}")   //this is path from application.properties file
    private String imagePath;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        //generate unique id in in string format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        //encoding password
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        //converting manually but latr we will learn mapper class concept
        //dto to entity
        User user = dtoToEntity(userDto);

        //fetch role of normal and set it to user
        Role role = roleRepository.findById(normalRoleId).get();
        user.getRoles().add(role);

        User userEntity = userRepository.save(user);

        //entity to dto
        UserDto dto = entityToDto(userEntity);
        return dto;
    }



    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with this id"));

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
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with this id"));

        //delete user profile image
        String fullPath = imagePath + user.getImage();
        //  now path like images/users/abc.png

        //if file is deleted then no problem if not then there is exception called NoSuchFileException
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex){
            logger.info("user image not found in the folder");
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //delete user
        userRepository.delete(user);
    }

    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {

        //sort is a class
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        //pageable is inteface so we cant create object....we use impmentation class PageRequest
        //and its of method to take page number and page size
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> page = userRepository.findAll(pageable);

//        //converting page into list
//        List<User> users = page.getContent();
//
//        //here we use steam api to convert list of user to list of dto...we can also use for each
//        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
//
//        PageableResponse<UserDto> response = new PageableResponse<>();
//        response.setContent(dtoList);
//        response.setPageNumber(page.getNumber());
//        response.setPageSize(page.getSize());
//        response.setTotalElements(page.getTotalElements());
//        response.setTotalPages(page.getTotalPages());
//        response.setLastPage(page.isLast());

        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        return response;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with this id"));
        UserDto userDto = entityToDto(user);
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user not found with this email"));
        UserDto userDto = entityToDto(user);
        return userDto;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> userList = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = userList.stream().map(users -> entityToDto(users)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public Optional<User> findUserByEmailOptional(String email) {
        return userRepository.findByEmail(email);
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
