package net.nemisolv.lib.payload;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private Boolean success;
    private String message;
    private Integer count;
    private T data;

    public static <T> ApiResponse<T> success(T data) {

        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .count(data instanceof Collection ? ((Collection<?>) data).size() : null)
                .build();
    }

    // no content
    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
                .success(true)
                .build();
    }

    // no content with message
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build();
    }


}