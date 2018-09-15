package com.ems.form;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Component
@Data
@EqualsAndHashCode(callSuper = false)
public class UserResponse extends AbstractForm implements Cloneable {
    private User user;
}
