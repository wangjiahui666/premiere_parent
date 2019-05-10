package com.premiere.mapper;

import com.premiere.pojo.Column;
import com.premiere.pojo.ColumnExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface ColumnMapper {
    int countByExample(ColumnExample example);

    int deleteByExample(ColumnExample example);

    int deleteByPrimaryKey(String id);

    int insert(Column record);

    int insertSelective(Column record);

    List<Column> selectByExample(ColumnExample example);

    Column selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Column record, @Param("example") ColumnExample example);

    int updateByExample(@Param("record") Column record, @Param("example") ColumnExample example);

    int updateByPrimaryKeySelective(Column record);

    int updateByPrimaryKey(Column record);

    @Update("update tb_column set state='1' where id=#{columnid}")
    void examineColumn(String columnid);
}