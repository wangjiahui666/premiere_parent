package com.premiere.mapper;

import com.premiere.pojo.Problem;
import com.premiere.pojo.ProblemExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ProblemMapper {
    int countByExample(ProblemExample example);

    int deleteByExample(ProblemExample example);

    int deleteByPrimaryKey(String id);

    int insert(Problem record);

    int insertSelective(Problem record);

    List<Problem> selectByExampleWithBLOBs(ProblemExample example);

    List<Problem> selectByExample(ProblemExample example);

    Problem selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Problem record, @Param("example") ProblemExample example);

    int updateByExampleWithBLOBs(@Param("record") Problem record, @Param("example") ProblemExample example);

    int updateByExample(@Param("record") Problem record, @Param("example") ProblemExample example);

    int updateByPrimaryKeySelective(Problem record);

    int updateByPrimaryKeyWithBLOBs(Problem record);

    int updateByPrimaryKey(Problem record);

    @Select("SELECT problemid FROM tb_pl where labelid =#{lable}")
    List<String> findProblemIdByLabel(Integer label);
}