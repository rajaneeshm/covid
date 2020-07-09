package com.evalution.covid.entity;

import com.evalution.covid.constant.UserType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Set;

@Entity
@Audited
@Setter
@Getter
public class User extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    @Transient
    private String passwordConfirm;
    @Enumerated(EnumType.STRING)
    private UserType userType;

}
