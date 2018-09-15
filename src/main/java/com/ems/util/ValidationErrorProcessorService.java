package com.ems.util;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class ValidationErrorProcessorService {

    public String process(BindingResult errors) {
        return errors.getFieldErrors()
                .stream()
                .map((e) -> "message: " + e.getDefaultMessage() + ", type: " + e.getObjectName() + ", field: " + e.getField())
                .collect(Collectors.toList()).toString();
    }
}
