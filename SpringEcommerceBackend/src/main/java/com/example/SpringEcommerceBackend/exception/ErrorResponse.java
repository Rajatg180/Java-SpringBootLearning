package com.example.SpringEcommerceBackend.exception;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
// this class is used to send structured error responses to the client when exceptions occur in the application. It contains fields for timestamp, status code, error message, and the path of the request that caused the error. The @Data annotation generates getters, setters, and other utility methods, while the @Builder annotation allows for easy construction of ErrorResponse objects using a builder pattern.
@Data
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;
}
