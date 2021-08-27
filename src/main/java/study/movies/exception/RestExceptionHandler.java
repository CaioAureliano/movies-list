package study.movies.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import study.movies.exception.http.BadRequestException;
import study.movies.exception.http.ForbiddenException;
import study.movies.exception.http.InternalException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log4j2
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(BadRequestException exception) {
        log.warn(exception.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), Collections.singletonList(exception.getLocalizedMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbidden(ForbiddenException exception) {
        log.warn("Permission Denied: " + exception.getMessage());
        return buildResponse(HttpStatus.FORBIDDEN, "Permission Denied", Collections.singletonList(exception.getMessage()));
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<Object> handleInternalServerError(InternalException exception) {
        log.error(exception);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error, please try again later", Collections.singletonList(exception.getLocalizedMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRunTimeException(RuntimeException exception) {
        log.error(exception);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ops...", Collections.singletonList("Internal Error"));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors().forEach(error -> errors.add(error.getObjectName() + ": " + error.getDefaultMessage()));
        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid fields", errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Fields not found or invalid", Collections.singletonList(ex.getLocalizedMessage()));
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String message, List<String> errors) {
        TemplateResponseError error = TemplateResponseError.builder()
                .code(status.value())
                .status(status.getReasonPhrase())
                .message(message)
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(status).body(error);
    }
}
