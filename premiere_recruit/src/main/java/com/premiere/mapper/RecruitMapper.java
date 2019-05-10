package com.premiere.mapper;

import com.premiere.pojo.Recruit;
import com.premiere.pojo.RecruitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RecruitMapper {
    int countByExample(RecruitExample example);

    int deleteByExample(RecruitExample example);

    int deleteByPrimaryKey(String id);

    int insert(Recruit record);

    int insertSelective(Recruit record);

    List<Recruit> selectByExample(RecruitExample example);

    Recruit selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Recruit record, @Param("example") RecruitExample example);

    int updateByExample(@Param("record") Recruit record, @Param("example") RecruitExample example);

    int updateByPrimaryKeySelective(Recruit record);

    int updateByPrimaryKey(Recruit record);
}