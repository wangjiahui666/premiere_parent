package com.premiere.mapper;

import com.premiere.pojo.User;
import com.premiere.pojo.UserExample;
import java.util.List;

import org.apache.ibatis.annotations.*;

public interface UserMapper {

    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    @Insert("insert into tb_follow set userid=#{id},targetuser=#{userId} ")
    void insertFollow(@Param("id") String id,@Param("userId") String userId);

    @Delete("delete from tb_follow where userid=#{id} and targetuser=#{userId}")
    void deleteFollow(@Param("id") String id,@Param("userId") String userId);


    @Select("select userid tb_follow where targetuser=#{userid}")
    List<String> followMyFollowId(String userid);

    @Select("select targetuser tb_follow where userid=#{userid}")
    List<String> followMyFans(String userid);

    @Update("update tb_user set fanscount=fanscount+#{num} where id=#{userId}")
    void incFollowCount(@Param("userId") String userId, @Param("num")Integer num);

    @Update("update tb_user set followcount=followcount+#{num} where id=#{userId}")
    void incFansCount(@Param("userId")String userId, @Param("num")Integer num);
}