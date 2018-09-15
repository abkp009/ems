package com.ems.response;

import lombok.Data;

@Data
public class GeneralResponse<T> {
    private T response;
    private String errors;
}
