package net.nemisolv.lib.core.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.nemisolv.lib.util.ResultCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalHandlerException extends ResponseEntityExceptionHandler {

    /**
     * Centralized error builder to avoid redundancy.
     */
    private ErrorDTO buildErrorDTO(HttpServletRequest request,
                                   ResultCode resultCode,
                                   List<String> errors) {
        return ErrorDTO.builder()
                .code(resultCode.code())
                .path(request.getRequestURI())
                .errors(errors)
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO handleGenericException(HttpServletRequest request, Exception ex) {
        log.error("Request: {} raised exception: {}", request.getRequestURL(), ex.getMessage(), ex);
        return buildErrorDTO(request, ResultCode.SERVER_INTERNAL_ERROR,
                List.of(ResultCode.SERVER_INTERNAL_ERROR.message()));
    }

    @ExceptionHandler(BadCredentialException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorDTO handleBadCredentialException(HttpServletRequest request, BadCredentialException ex) {
        log.error("Request: {} raised exception: {}", request.getRequestURL(), ex.getMessage(), ex);
        return buildErrorDTO(request, ex.getResultCode(),
                List.of(ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDTO handleResourceNotFoundException(HttpServletRequest request, ResourceNotFoundException ex) {
        log.error("Request: {} raised exception: {}", request.getRequestURL(), ex.getMessage(), ex);
        return buildErrorDTO(request, ex.getResultCode(),
                List.of(ex.getMessage()));
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        ErrorDTO error = ErrorDTO.builder()
                .code(ResultCode.METHOD_ARGUMENT_NOT_VALID.code())
                .path(((ServletWebRequest) request).getRequest().getServletPath())
                .errors(errors)
                .build();
        return new ResponseEntity<>(error, headers, status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException ex) {
        log.error("Request: {} raised exception: {}", request.getRequestURI(), ex.getMessage(), ex);
        return buildErrorDTO(request, ResultCode.ILLEGAL_ARGUMENT,
                List.of(ResultCode.ILLEGAL_ARGUMENT.message()));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleBadRequestException(HttpServletRequest request, BadRequestException ex) {
        log.error("Request: {} raised exception: {}", request.getRequestURI(), ex.getMessage(), ex);
        return buildErrorDTO(request,ex.getResultCode(),
                List.of(ex.getMessage()));
    }

//    @ExceptionHandler(value = {AuthorizationDeniedException.class})
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ResponseBody
//    public ErrorDTO handleAuthorizationDeniedException(HttpServletRequest request, AuthorizationDeniedException ex) {
//        log.error("Request: {} raised exception: {}", request.getRequestURI(), ex.getMessage(), ex);
//        return buildErrorDTO(request, ResultCode.USER_PERMISSION_ERROR,
//                List.of(ResultCode.USER_PERMISSION_ERROR.message()));
//    }

    @ExceptionHandler(PermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorDTO handlePermissionException(HttpServletRequest request, PermissionException ex) {
        log.error("Request: {} raised exception: {}", request.getRequestURI(), ex.getMessage(), ex);
        return buildErrorDTO(request, ex.getResultCode(),
                List.of(ex.getMessage()));
    }

}
