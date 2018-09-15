package com.ems.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "user_details", catalog = "ems")
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends AuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "user_name")
    private String userName;
    private String password;
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Column(name = "user_type")
    private String userType;
    private String gender;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role_mapping", joinColumns = @JoinColumn(name = "user_name"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<UserRoleEntity> roles = new ArrayList<UserRoleEntity>();
    @Column(insertable = false)
    private String userId = "Abhishek";

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void clear() {
        userName = "";
        password = "";
        dob = null;
        userType = "";
        gender = "";
    }

}
