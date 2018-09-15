package com.ems.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "department", catalog = "ems")
@Data
public class DepartmentEntity {
    @Id
    @Column(name = "department_id")
    private Integer departmentId = 0;
    @Column(name = "department_name")
    private String departmentName;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hod")
    private EmployeeEntity hod;

}
