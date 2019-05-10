package com.premiere.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
public class Spit implements Serializable {
    private static final long serialVersionUID = -8054121587127582285L;

    @Id
    private String _id;

    private String content; //文档

    private Date publishtime;//发布时间

    private String userid;//用户ID

    private String nickname;//昵称

    private Integer visits;//访问量

    private Integer thumbup;//点赞

    private Integer share;// 分享

    private Integer comment;//评论

    private String state;//状态

    private String parentid;// 父级ID
}
