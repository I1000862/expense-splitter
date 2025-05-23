package com.example.expensesplitter.exception;

import com.example.expensesplitter.dto.response.ErrorResponseDto;
import com.example.expensesplitter.dto.response.ValidationErrorDetail;
import com.example.expensesplitter.dto.response.ValidationErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(new ErrorResponseDto("NotFound", e.getMessage()));
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidUuid(InvalidIdException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new ErrorResponseDto("BadRequest", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDto> handleValidationException(MethodArgumentNotValidException e) {
        List<ValidationErrorDetail> errorDetails = e.getBindingResult().getFieldErrors()
                                                    .stream()
                                                    .map(fieldError -> new ValidationErrorDetail(
                                                            fieldError.getField(), fieldError.getDefaultMessage()
                                                    ))
                                                    .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new ValidationErrorResponseDto("BadRequest", "Validation failed.", errorDetails));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleRequestBodyMissingException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new ErrorResponseDto("BadRequest", "Required request body is missing."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(new ErrorResponseDto("INTERNAL_SERVER_ERROR", e.getMessage()));
    }
}
