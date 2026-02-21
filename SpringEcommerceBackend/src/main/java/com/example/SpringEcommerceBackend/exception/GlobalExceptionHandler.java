package com.example.SpringEcommerceBackend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import java.time.LocalDateTime;

// this fill handle global exceptions and send a custom response to the client
@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(DuplicateEmailException.class)
        public ResponseEntity<ErrorResponse> handleDuplicateEmail(
                        DuplicateEmailException ex,
                        HttpServletRequest request) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleNotFound(
                        ResourceNotFoundException ex,
                        HttpServletRequest request) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.NOT_FOUND.value())
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidation(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                String errorMessage = ex.getBindingResult()
                                .getFieldError()
                                .getDefaultMessage();

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(errorMessage)
                                .path(request.getRequestURI())
                                .build();

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGeneral(
                        Exception ex,
                        HttpServletRequest request) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message("Something went wrong")
                                .path(request.getRequestURI())
                                .build();

                return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(CartEmptyException.class)
        public ResponseEntity<ErrorResponse> handleCartEmpty(
                        CartEmptyException ex,
                        HttpServletRequest request) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(InsufficientStockException.class)
        public ResponseEntity<ErrorResponse> handleStock(
                        InsufficientStockException ex,
                        HttpServletRequest request) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ErrorResponse> handleAccessDenied(
                        AccessDeniedException ex,
                        HttpServletRequest request) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.FORBIDDEN.value())
                                .message("Forbidden: You don't have permission")
                                .path(request.getRequestURI())
                                .build();

                return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }

        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<ErrorResponse> handleAuthException(
                        AuthenticationException ex,
                        HttpServletRequest request) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .message("Unauthorized: Please login")
                                .path(request.getRequestURI())
                                .build();

                return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
}
