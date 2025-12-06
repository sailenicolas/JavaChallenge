package com.empresa.core.dtos.responses;

import com.empresa.core.exceptions.ApiError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder

public class ApiResponse<T> extends ApiError {
    private T data;

    public ApiResponse(T data) {
        super();
        this.data = data;
        this.setErrors(null);
        this.setMessage("Successful Call");
    }
}
