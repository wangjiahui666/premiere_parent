package com.premiere.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface FriendMapper {

    @Select("INSERT INTO tb_friend set userid=#{userid},friendid=#{friendid},islike=#{type}")
    void insertLikeFriend(@Param("userid")String userid,@Param("friendid") String friendid,@Param("type") String type);

    @Delete("delete from tb_friend where userid=#{userid} and friendid=#{friendid}")
    void deleteLikeFriend(@Param("userid")String userid,@Param("friendid") String friendid);
}
