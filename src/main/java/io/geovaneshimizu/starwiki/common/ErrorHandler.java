package io.geovaneshimizu.starwiki.common;

import static java.lang.System.currentTimeMillis;

import org.slf4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ErrorHandler {

    private final Logger logger;

    ErrorHandler(Logger logger) {
        this.logger = logger;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception,
                                                                 ServerWebExchange exchange) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ServerHttpRequest request = exchange.getRequest();
        ErrorResponse errorResponse =
                new ErrorResponse(
                        currentTimeMillis(),
                        request.getPath().value(),
                        status.value(),
                        status.getReasonPhrase(),
                        exception.getMessage());

        logger.warn("{}", errorResponse);

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(NullPointerException.class)
    ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException exception,
                                                             ServerWebExchange exchange) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ServerHttpRequest request = exchange.getRequest();
        ErrorResponse errorResponse =
                new ErrorResponse(
                        currentTimeMillis(),
                        request.getPath().value(),
                        status.value(),
                        status.getReasonPhrase(),
                        exception.getMessage());

        logger.error("{}", errorResponse, exception);

        return new ResponseEntity<>(errorResponse, status);
    }
}
