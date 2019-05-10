package com.premiere.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_admin")
@Data
public class Admin implements Serializable {
    private static final long serialVersionUID = -3714203541951313229L;

    @Id
    private String id;

    @Column(name = "loginname")
    private String loginname;

    @Column(name = "password")
    private String password;

    @Column(name = "state")
    private String state;

}