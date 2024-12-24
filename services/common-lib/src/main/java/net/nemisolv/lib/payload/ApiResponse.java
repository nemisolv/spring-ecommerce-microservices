package net.nemisolv.lib.payload;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.nemisolv.lib.util.ResultCode;

import java.util.Collection;

@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int  code;
    private String message;
    private Integer countRecord;
    private T data;

    public static <T> ApiResponse<T> success(T data) {

        return ApiResponse.<T>builder()
                .code(ResultCode.SUCCESS.code())
                .data(data)
                .countRecord(data instanceof Collection ? ((Collection<?>) data).size() : null)
                .build();
    }

    // no content
    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
                .code(ResultCode.SUCCESS.code())
                .build();
    }

    // no content with message
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .code(ResultCode.SUCCESS.code())
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .code(ResultCode.SERVER_INTERNAL_ERROR.code())
                .message(message)
                .build();
    }


}