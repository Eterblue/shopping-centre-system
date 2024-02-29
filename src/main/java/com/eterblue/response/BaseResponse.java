package com.eterblue.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> implements Serializable {

    private String msg;
    private Boolean success;
    private T data;

    public static <T> BaseResponse<T> success() {
        BaseResponse<T> response = new BaseResponse<>();
        response.success=true;
        return response;
    }
    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.success=true;
        response.data=data;
        return response;
    }
    public static <T> BaseResponse<T> error(String msg) {
        BaseResponse<T> response = new BaseResponse<>();
        response.success=false;
        response.msg=msg;
        return response;
    }
}
