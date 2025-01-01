package com.shop.aspect;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 프로젝트 전역에 대한 예외처리 담당 Aspect [스프링 프레임워크 제공]
 * AOP와 유사한 방식으로 동작하지만 주로 컨트롤러 계층에만 적용됨.
 * 
 * @ControllerAdvice : ExceptionHandlerExceptionResolver 이용. "org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver"
 * @ExceptionHandler : 
 */
@ControllerAdvice
public class GlobalExceptionAspect {
    
    /**
     * @param DataAccessException
     * @return { INTERNAL_SERVER_ERROR }
     */ 
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException(DataAccessException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("A database error occurred: " + exception.getMessage());
    }

    /**
     * @param NullPointerException
     * @return { BAD_REQUEST }
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("A null pointer exception occurred: " + exception.getMessage());
    }

    /**
     * @param IllegalArgumentException
     * @return { BAD_REQUEST }
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid argument: " + exception.getMessage());
    }

    /**
     * @param Exception
     * @return { INTERNAL_SERVER_ERROR }
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexceptionpected error occurred: " + exception.getMessage());
    }
}