package com.example.practice.authentication.advices;

import com.example.practice.authentication.exception.BusinessException;
import com.example.practice.authentication.exception.ErrorCodes;
import com.example.practice.authentication.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    private final Locale currentLocale = LocaleContextHolder.getLocale();

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptionMethod(final Exception ex, final WebRequest requset) {
        ApiResponse exceptionMessageObj = new ApiResponse();
        StringBuilder sb = new StringBuilder();
        String errorMessage = ex.getMessage();
        sb.append(errorMessage);
        exceptionMessageObj.setStatusCode(HttpStatus.BAD_REQUEST.value());
        exceptionMessageObj.setErrorCode(ErrorCodes.NOT_NULL_VALIDATION.getValue());
        exceptionMessageObj.setMessage(sb.toString());
        exceptionMessageObj.setError(ex.getClass().getCanonicalName());
        exceptionMessageObj.setPath(((ServletWebRequest) requset).getRequest().getServletPath());
        exceptionMessageObj.setErrorType(ErrorCodes.INVALID_DATA.name());
        return new ResponseEntity<>(exceptionMessageObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse> handleBusinessException(final BusinessException exc, final WebRequest requset) {
        String errorMessage = ErrorCodes.BUSINESS_EXCEPTION.name();
        try {
            errorMessage = messageSource.getMessage(exc.getMessage(), exc.getArgs(), currentLocale);
        }catch (Exception e) {
           log.info("Rest Controller Advice Exception {}",errorMessage);
        }
        return ResponseEntity.status(Objects.nonNull(exc.getStatus()) ? exc.getStatus() : HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(exc.getStatus().value(), exc.getStatus().getReasonPhrase(), errorMessage,
                        ((ServletWebRequest) requset).getRequest().getServletPath(), exc.getCode(), exc.getMessage(), LocalDateTime.now()));
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex, final WebRequest requset) {
        ApiResponse exceptionMessageObj = new ApiResponse();
        exceptionMessageObj.setStatusCode(HttpStatus.BAD_REQUEST.value());
        exceptionMessageObj.setErrorCode(ErrorCodes.NOT_NULL_VALIDATION.getValue());
        exceptionMessageObj.setMessage(ex.getMessage());
        exceptionMessageObj.setError(ex.getClass().getCanonicalName());
        exceptionMessageObj.setPath(((ServletWebRequest) requset).getRequest().getServletPath());
        return new ResponseEntity<>(exceptionMessageObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}

