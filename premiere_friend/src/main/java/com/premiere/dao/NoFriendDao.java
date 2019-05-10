package com.premiere.dao;

import com.premiere.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NoFriendDao extends JpaRepository<NoFriend,String>, JpaSpecificationExecutor<NoFriend> {

}
