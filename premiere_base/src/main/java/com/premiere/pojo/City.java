package com.premiere.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_city")
@Data
public class City implements Serializable {
    private static final long serialVersionUID = 1481105105446645608L;

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "ishot")
    private String ishot;

}