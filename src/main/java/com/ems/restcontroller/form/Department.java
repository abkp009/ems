package com.ems.restcontroller.form;

import com.ems.form.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Department {
    private Integer departmentId = 0;
    private String departmentName;
    private Employee hod;
}
