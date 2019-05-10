package com.premiere.mapper;

import com.premiere.pojo.Gathering;
import com.premiere.pojo.GatheringExample;
import com.premiere.pojo.GatheringWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface GatheringMapper {
    int countByExample(GatheringExample example);

    int deleteByExample(GatheringExample example);

    int deleteByPrimaryKey(String id);

    int insert(GatheringWithBLOBs record);

    int insertSelective(GatheringWithBLOBs record);

    List<GatheringWithBLOBs> selectByExampleWithBLOBs(GatheringExample example);

    List<Gathering> selectByExample(GatheringExample example);

    GatheringWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") GatheringWithBLOBs record, @Param("example") GatheringExample example);

    int updateByExampleWithBLOBs(@Param("record") GatheringWithBLOBs record, @Param("example") GatheringExample example);

    int updateByExample(@Param("record") Gathering record, @Param("example") GatheringExample example);

    int updateByPrimaryKeySelective(GatheringWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(GatheringWithBLOBs record);

    int updateByPrimaryKey(Gathering record);

    @Select("SELECT g.id,g.name,g.summary,g.detail,g.sponsor,g.image,g.starttime,g.endtime,g.address,g.enrolltime,g.state,g.city FROM tb_gathering AS g LEFT JOIN tb_usergath AS u ON g.id = u.gathid WHERE u.userid =#{city}")
    List<Gathering> findGatheringByCity(String city);
}