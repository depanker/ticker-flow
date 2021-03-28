package com.depanker.ticker.exceptions;


import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class ExceptionAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<InputValidation> handleBadInputException(MethodArgumentNotValidException e, HandlerMethod handlerMethod) {
        List<InputValidation> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new InputValidation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        log.warn("Input validation error occurred while request from {}.{}(), error: {}", handlerMethod.getBeanType()
                .getSimpleName(), handlerMethod.getMethod().getName(), errors.toString());
        return errors;
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<InputValidation> handleBadInputException(MissingServletRequestPartException e, HandlerMethod handlerMethod) {
        List<InputValidation> errors = Arrays.asList(new InputValidation(e.getRequestPartName(), e.getMessage()));
        log.warn("Input validation error occurred while request from {}.{}(), error: {}", handlerMethod.getBeanType()
                .getSimpleName(), handlerMethod.getMethod().getName(), errors.toString());
        return errors;
    }

    @ExceptionHandler(InvalidTickExceptions.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    ApplicationExceptionResponse applicationException(InvalidTickExceptions e) {
        log.error(e.getMessage(), e);
        return new ApplicationExceptionResponse(e.getMessage());
    }

    @ExceptionHandler(OperationFailedException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    ApplicationExceptionResponse internalError(OperationFailedException e) {
        log.error(e.getMessage(), e);
        return new ApplicationExceptionResponse(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    ApplicationExceptionResponse unHandeled(Exception e) {
        log.error(e.getMessage(), e);
        return new ApplicationExceptionResponse(e.getMessage());
    }

    @ExceptionHandler(RuntimeJsonMappingException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    ApplicationExceptionResponse invalidFormat(RuntimeJsonMappingException e) {
        log.error(e.getMessage(), e);
        return new ApplicationExceptionResponse("Unable to parse the data");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    List<InputValidation> invalidFormat(ConstraintViolationException e) {
        List<InputValidation> errors = e.getConstraintViolations().stream()
                .map(constraintViolation -> new InputValidation(constraintViolation.getPropertyPath().toString(),
                        constraintViolation.getMessage()))
                .collect(Collectors.toList());
        log.error(e.getMessage(), e);
        return errors;
    }

    @ExceptionHandler(JSQLParserException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    ApplicationExceptionResponse invalidSqlException(JSQLParserException e) {
        log.error(e.getMessage(), e);
        return new  ApplicationExceptionResponse("Invalid SQL, provided.");
    }


}
