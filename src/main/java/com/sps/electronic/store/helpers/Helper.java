package com.sps.electronic.store.helpers;

import com.sps.electronic.store.dtos.PageableResponse;
import com.sps.electronic.store.dtos.UserDto;
import com.sps.electronic.store.enitities.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type){

        //converting page into list
//        List<User> users = page.getContent();
        List<U> entity = page.getContent();

        //here we use steam api to convert list of user to list of dto...we can also use for each
//        List<UserDto> dtoList = entity.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        List<V> dtoList = entity.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());


//        PageableResponse<UserDto> response = new PageableResponse<>();
        PageableResponse<V> response = new PageableResponse<>();

        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }
}
