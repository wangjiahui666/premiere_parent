package com.premiere.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.premiere.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    User findOneById(String id);

    User findUserByMobile(String mobile);
}
