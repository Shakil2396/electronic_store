package com.sps.electronic.store.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {

    private String imageName;
    private String message;
    private boolean success;
    private HttpStatus status;

    // Private constructor to force use of the builder
    private ImageResponse(Builder builder) {
        this.imageName = builder.imageName;
        this.message = builder.message;
        this.success = builder.success;
        this.status = builder.status;
    }

    // Getters
    public String getImageName() {
        return imageName;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public HttpStatus getStatus() {
        return status;
    }

    // Builder class
    public static class Builder {
        private String imageName;
        private String message;
        private boolean success;
        private HttpStatus status;

        // Builder setter methods
        public Builder imageName(String imageName) {
            this.imageName = imageName;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        // Build method to create an ImageResponse instance
        public ImageResponse build() {
            return new ImageResponse(this);
        }
    }

    // Static builder method to access the builder
    public static Builder builder() {
        return new Builder();
    }

}
