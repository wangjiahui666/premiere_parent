package com.premiere.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "tb_label")
@Data
public class Label implements Serializable {

    private static final long serialVersionUID = 5888449658231222711L;

    @Id
    private String id;

    @Column(name = "labelname")
    private String labelname;

    @Column(name = "state")
    private String state;

    @Column(name = "count")
    private Long count;

    @Column(name = "recommend")
    private String recommend;

    @Column(name = "fans")
    private Long fans;
}
