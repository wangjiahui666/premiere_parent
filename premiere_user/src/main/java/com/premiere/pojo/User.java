package com.premiere.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_user")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 4313251453496155780L;

    @Id
    private String id;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "sex")
    private String sex;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "email")
    private String email;

    @Column(name = "regdate")
    private Date regdate;

    @Column(name = "updatedate")
    private Date updatedate;

    @Column(name = "lastdate")
    private Date lastdate;

    @Column(name = "online")
    private Long online;

    @Column(name = "interest")
    private String interest;

    @Column(name = "personality")
    private String personality;

    @Column(name = "fanscount")
    private Integer fanscount;

    @Column(name = "followcount")
    private Integer followcount;

}