package com.sps.electronic.store.dtos;


import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseMessage {

    private String message;
    private boolean success;
    private HttpStatus status;

    // Private constructor to force use of builder
    private ApiResponseMessage(Builder builder) {
        this.message = builder.message;
        this.success = builder.success;
        this.status = builder.status;
    }

    // Getters (optional, but recommended)
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
        private String message;
        private boolean success;
        private HttpStatus status;

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

        public ApiResponseMessage build() {
            return new ApiResponseMessage(this);
        }
    }

    // Static builder method
    public static Builder builder() {
        return new Builder();
    }
}
