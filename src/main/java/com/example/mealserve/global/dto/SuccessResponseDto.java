package com.example.mealserve.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponseDto<T> {
    private final String message;
    private final T data;
    public SuccessResponseDto(String message, T data){
        this.message = message;
        this.data = data;
    }
}
