package com.sps.electronic.store.dtos;

import com.sps.electronic.store.enitities.Order;
import com.sps.electronic.store.enitities.Role;
import com.sps.electronic.store.validate.ImageNameValid;
import com.sps.electronic.store.validate.UniqueEmail;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min = 3, max = 15, message = "invalid name!!")
    @ApiModelProperty(value = "user_name", name = "username", required = true, notes = "user name of new user !!")
    private String name;

//    @Email(message = "invalid user email !!")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "invalid email !!")
    @NotBlank(message = "email is required !!")
    @UniqueEmail(message = "This email is already taken. Please try another.")
    private String email;

    @NotBlank(message = "password is required !!")
    private String password;

    @Size(min = 4, max = 6, message = "invalid gender !!") //male 4 and female 6
    private String gender;

    @NotBlank(message = "write something about yourself !!")
    private String about;

    @ImageNameValid              //also we can throw message here or automatically gets default message
    private String image;

    private Set<RoleDto> roles = new HashSet<>();
}
