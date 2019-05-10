package com.premiere.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.premiere.pojo.Admin;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface AdminDao extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {

    Admin findOneById(String id);

    Admin findAdminByLoginnameAndPassword(String loginname, String password);

    Admin findAdminByLoginname(String loginname);
}
