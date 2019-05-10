package com.premiere.dao;

import com.premiere.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String>, JpaSpecificationExecutor<Friend> {

    @Query("select count(f) from Friend f where f.userid=?1 and f.friendid=?2")
    Integer selectCount(String userid,String friendid);

    @Modifying
    @Query("update Friend f set f.islike=?3 where f.userid=?1 and f.friendid=?2")
    void updateLike(String userid,String friendid,String islike);

    @Modifying
    @Query("delete from Friend f where f.userid=?1 and f.friendid=?2")
    void deleteFriend(String userid,String friendid);
}
