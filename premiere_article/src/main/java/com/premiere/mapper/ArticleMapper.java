package com.premiere.mapper;

import com.premiere.pojo.Article;
import com.premiere.pojo.ArticleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ArticleMapper {
    int countByExample(ArticleExample example);

    int deleteByExample(ArticleExample example);

    int deleteByPrimaryKey(String id);

    int insert(Article record);

    int insertSelective(Article record);

    List<Article> selectByExampleWithBLOBs(ArticleExample example);

    List<Article> selectByExample(ArticleExample example);

    Article selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByExampleWithBLOBs(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByExample(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    @Update("UPDATE tb_article set thumbup=thumbup+1  where id=#{id} ")
    void thumbUp(String id);

    @Select("SELECT * FROM tb_article where channelid=#{id}")
    List<Article> findChannelArticle(Integer channelId);

    @Select("SELECT * FROM tb_article where columnid=#{id}")
    List<Article> findColumnArticle(Integer columnId);

    @Select("SELECT * FROM tb_article where istop=#{istop}")
    List<Article> findTopArticle(String istop);

    @Update("UPDATE tb_article set state='1' where id=#{id}")
    void examineArticle(String id);
}