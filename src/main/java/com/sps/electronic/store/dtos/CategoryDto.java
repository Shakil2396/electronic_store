package com.sps.electronic.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private String categoryId;

    @NotBlank
    @Size(min = 4, message = "title must be of minimum 4 characters")
    private String title;

    @NotBlank(message = "description required !!")
    private String description;

    private String coverImage;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public @NotBlank @Min(value = 4, message = "title must be of minimum 4 characters !!") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank @Min(value = 4, message = "title must be of minimum 4 characters !!") String title) {
        this.title = title;
    }

    public @NotBlank(message = "description required !!") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "description required !!") String description) {
        this.description = description;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
