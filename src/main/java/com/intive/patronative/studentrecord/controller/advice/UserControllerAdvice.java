package com.intive.patronative.studentrecord.controller.advice;

import com.intive.patronative.studentrecord.exception.AlreadyExistsException;
import com.intive.patronative.studentrecord.exception.EntityNotFoundException;
import com.intive.patronative.studentrecord.exception.InvalidArgumentException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class UserControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidArgumentException.class)
    public ValidationErrorResponse invalidArgumentHandler(final InvalidArgumentException exception) {
        return buildErrorResponse(exception.getFieldErrors());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ValidationErrorResponse entityNotFoundHandler(final EntityNotFoundException exception) {
        return buildErrorResponse(Collections.singletonList(exception.getFieldError()));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistsException.class)
    public ValidationErrorResponse alreadyExistException(final AlreadyExistsException exception) {
        return buildErrorResponse(Collections.singletonList(exception.getFieldError()));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity<ValidationErrorResponse> fileSizeLimitExceededHandler(final FileSizeLimitExceededException exception) {
        return ResponseEntity
                .unprocessableEntity()
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .body(buildErrorResponse((new ViolationError(exception.getFieldName(), exception.getFileName(), exception.getMessage()))));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<ValidationErrorResponse> sizeLimitExceededExceptionHandler(final SizeLimitExceededException exception) {
        return ResponseEntity
                .unprocessableEntity()
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .body(buildErrorResponse((new ViolationError("image", null, exception.getMessage()))));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ValidationErrorResponse {
        private List<ViolationError> violationErrors;
    }

    @Value
    private static class ViolationError {
        String fieldName;
        Object rejectedValue;
        String message;
    }

    private ValidationErrorResponse buildErrorResponse(final List<FieldError> fieldErrors) {
        return (fieldErrors == null)
                ? new ValidationErrorResponse()
                : new ValidationErrorResponse(fieldErrors.stream().map(fieldError -> new ViolationError(fieldError.getField(),
                fieldError.getRejectedValue(), fieldError.getDefaultMessage())).collect(Collectors.toList()));
    }

    private ValidationErrorResponse buildErrorResponse(final ViolationError violationError) {
        return new ValidationErrorResponse(Collections.singletonList(violationError));
    }

}