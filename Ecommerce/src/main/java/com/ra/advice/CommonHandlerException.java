package com.ra.advice;

import com.ra.exception.BaseException;
import com.ra.exception.Message;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Order(value = PriorityOrdered.HIGHEST_PRECEDENCE)
public class CommonHandlerException extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonHandlerException.class);

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handlerException(Throwable ex, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        BaseException baseException;
        if (ex instanceof BaseException) {
            baseException = (BaseException) ex;
            return new ResponseEntity<>(baseException.getErrorMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        baseException = new BaseException("RA-00-05");
        return new ResponseEntity<>(baseException.getErrorMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException ex) {
        final List<String> errors = ex.getConstraintViolations()
                .parallelStream()
                .map(violation -> violation
                        .getPropertyPath() + " : " + new BaseException(violation.getMessage()).getErrorMessage().getMessage())
                .collect(Collectors.toList());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<Object> handleDataIntegrityViolationException(final DataIntegrityViolationException ex) {
        final String errors = ex.getRootCause().getMessage();
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleBadCredentialsException(final BadCredentialsException ex) {
        BaseException baseException = new BaseException("RA-00-01");
        return new ResponseEntity<>(baseException.getErrorMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<Object> handleEntityNotFoundException(final EntityNotFoundException ex) {
        BaseException baseException = new BaseException("RA-00-02");
        return new ResponseEntity<>(baseException.getErrorMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<Object> handleNoSuchElementException(final NoSuchElementException ex) {
        BaseException baseException = new BaseException("RA-00-02");
        return new ResponseEntity<>(baseException.getErrorMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<Object> handleNullPointerException(final NullPointerException ex) {
        BaseException baseException = new BaseException("RA-00-02");
        return new ResponseEntity<>(baseException.getErrorMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public final ResponseEntity<Object> handleInvalidDataAccessApiUsageException(final InvalidDataAccessApiUsageException ex) {
        BaseException baseException = new BaseException("RA-00-02");
        return new ResponseEntity<>(baseException.getErrorMessage(), HttpStatus.NOT_FOUND);
    }
}
