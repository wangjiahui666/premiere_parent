package com.premiere.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_nofriend")
@IdClass(NoFriend.class)
@Data
public class NoFriend implements Serializable {
    private static final long serialVersionUID = -601266933754473984L;

    @Id
    private String userid;

    @Id
    private String friendid;
}
