package com.example.practice.authentication.advices;

import com.example.practice.authentication.model.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        String path = request.getURI().getPath();
        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
            return body; // Return the body as is for Swagger endpoints
        }

        if(body instanceof ApiResponse){
            return body;
        }

        String timestamp = "2024-07-08T02:30:35.9804044";

        // Parse the timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
        LocalDateTime localDateTime = LocalDateTime.parse(timestamp, formatter);

        // Convert LocalDateTime to Instant
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

        // Get the epoch milliseconds
        long epochMillis = instant.toEpochMilli();

        System.out.println("Epoch Milliseconds: " + epochMillis);

        return ApiResponse.builder().data(body).statusCode(((ServletServerHttpResponse) response).getServletResponse().getStatus()).build();
    }
}
