package com.sps.electronic.store.services;

import com.sps.electronic.store.dtos.CategoryDto;
import com.sps.electronic.store.dtos.PageableResponse;

public interface CategoryService {
    
    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto, String categoryId);

    //delete
    void delete(String categoryId);

    //get all
    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get a single category details
    CategoryDto get(String categoryId);

    //search
}
