package com.ems.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "employee", catalog = "ems")
@Data
public class EmployeeEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private String department;
    private Integer salary;
}
