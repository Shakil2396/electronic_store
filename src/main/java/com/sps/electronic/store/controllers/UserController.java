package com.sps.electronic.store.controllers;


import com.sps.electronic.store.dtos.ApiResponseMessage;
import com.sps.electronic.store.dtos.ImageResponse;
import com.sps.electronic.store.dtos.PageableResponse;
import com.sps.electronic.store.dtos.UserDto;
import com.sps.electronic.store.services.FileService;
import com.sps.electronic.store.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/users")
//@CrossOrigin(origins = "*")
@Api(value = "UserController", description = "REST APIs related to perform user operatins!! ")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}") //this path we define in application.properties file
    private String imageUploadPath; //for this path update configuration file...so we can dynamically set the path

    @Autowired
    private UserService userService;

    //create
    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success | Ok"),
            @ApiResponse(code = 401, message = "not authorized !!"),
            @ApiResponse(code = 201, message = "new user created")
    })
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(user , HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId, @RequestBody UserDto userDto) {
        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId){
        userService.deleteUser(userId);

        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("user is deleted successfully")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    //get all users
    @GetMapping
    @ApiOperation(value = "get all users", response = ResponseEntity.class, tags = {"user-controller", "user api"})
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            //requered false means its not necessary to take it always take default value if we will not send value
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        //now send page number nad page size in user service
        return new ResponseEntity<>(userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
    }

    //get single user
    @GetMapping("/{userId}")
    @ApiOperation(value = "get single user by userid !!")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") String userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    //get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){ //no need to pass the parameter if name is same        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    //search user
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords){ //no need to pass the parameter if name is same        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
        return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);
    }

    //upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException {
        String imageName = fileService.uploadFile(image, Path.of(imageUploadPath));

        UserDto user = userService.getUserById(userId);
        user.setImage(imageName);
        UserDto userDto = userService.updateUser(user, userId);

        ImageResponse imageResponse = ImageResponse.builder()
                .imageName(imageName)
                .message("image is uploaded successfully !!")
                .success(true)
                .status(HttpStatus.CREATED)
                .build();
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }


    //serve user image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {

        UserDto user = userService.getUserById(userId);
        logger.info("User image name: {}", user.getImage());

        InputStream resource = fileService.getResource(imageUploadPath, user.getImage());

        //put this file in http servlet response
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream()); //copy from resource to response

    }

}
