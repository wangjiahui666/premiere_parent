package com.premiere.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.premiere.common.IdWorker;
import org.springframework.transaction.annotation.Transactional;
import com.premiere.dao.AdminDao;
import com.premiere.pojo.Admin;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
@CacheConfig(cacheNames = "admin")
public class AdminService {

    private final AdminDao adminDao;
    private final IdWorker idWorker;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdminService(AdminDao adminDao, IdWorker idWorker, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminDao = adminDao;
        this.idWorker = idWorker;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Admin> findAll() {
        return adminDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return Page<Admin>
     */
    public Page<Admin> findSearch(Map whereMap, int page, int size) {
        Specification<Admin> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return adminDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap 查询的条件
     * @return List<Admin>
     */

    public List<Admin> findSearch(Map whereMap) {
        Specification<Admin> specification = createSpecification(whereMap);
        return adminDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return Admin
     */
    @Cacheable(value = "Admin", key = "#id")
    public Admin findById(String id) {
        return adminDao.findOneById(id);
    }

    /**
     * 增加
     *
     * @param admin admin
     */
    public void add(Admin admin) {
        admin.setId(idWorker.nextId() + "");
        admin.setPassword(encoderPwd(admin.getPassword()));
        adminDao.save(admin);
    }

    /**
     * 修改
     *
     * @param admin admin
     */
    @CacheEvict(value = "Admin", key = "#admin.id")
    public void update(Admin admin) {
        adminDao.save(admin);
    }

    /**
     * 删除
     *
     * @param id id
     */
    @CacheEvict(value = "Admin", key = "#id")
    public void deleteById(String id) {
        adminDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap 动态条件
     * @return Specification<Admin>
     */
    private Specification<Admin> createSpecification(Map searchMap) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 登陆名称
            if (searchMap.get("loginname") != null && !"".equals(searchMap.get("loginname"))) {
                predicateList.add(cb.like(root.get("loginname").as(String.class), "%" + searchMap.get("loginname") + "%"));
            }
            // 密码
            if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                predicateList.add(cb.like(root.get("password").as(String.class), "%" + searchMap.get("password") + "%"));
            }
            // 状态
            if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%" + searchMap.get("state") + "%"));
            }

            int size = predicateList.size();
            return cb.and(predicateList.toArray(new Predicate[size]));

        };

    }

    public Admin login(Admin admin) {
        Admin adminByLoginname = adminDao.findAdminByLoginname(admin.getLoginname());
        if (null != adminByLoginname && bCryptPasswordEncoder.matches(admin.getPassword(), adminByLoginname.getPassword())) {
            return adminByLoginname;
        }
        return null;
    }

    private String encoderPwd(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
