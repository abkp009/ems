package com.ems.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.ems.entity.UserRoleEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Component
@Data
@ToString
@EqualsAndHashCode
public class User implements Cloneable {
    @NotNull
    @Size(min = 3, max = 10)
    private String userName;
    @NotNull
    @Size(min = 3, max = 10)
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Past
    private Date dob;
    private String userType;
    @JsonIgnore
    private Boolean remember;
    private Integer gender;
    private List<UserRoleEntity> roles = new ArrayList<UserRoleEntity>();

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void clear() {
        userName = "";
        password = "";
        dob = null;
        userType = "";
        remember = null;
        gender = null;
    }
}
