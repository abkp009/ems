package com.ems.form;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public abstract class AbstractForm {
    private String error;
}
