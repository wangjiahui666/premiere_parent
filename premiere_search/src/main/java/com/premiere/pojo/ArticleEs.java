package com.premiere.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

@Data
@Document(indexName = "aisile", type = "article")
public class ArticleEs implements Serializable {
    private static final long serialVersionUID = 7257497446315041225L;

    @Id
    private String id;//ID  对应的是数据库的ID
    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;//标题
    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;//文章正文
    private String state;//审核状态


}
