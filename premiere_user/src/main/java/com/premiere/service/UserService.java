package com.premiere.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.Predicate;

import cn.hutool.core.util.RandomUtil;
import com.premiere.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.premiere.common.IdWorker;
import org.springframework.transaction.annotation.Transactional;
import com.premiere.dao.UserDao;
import com.premiere.pojo.User;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
@CacheConfig(cacheNames = "user")
public class UserService {

    private final UserDao userDao;
    private final IdWorker idWorker;
    private final UserMapper userMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserDao userDao, IdWorker idWorker, UserMapper userMapper, RedisTemplate<String, String> redisTemplate, RabbitTemplate rabbitTemplate, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.idWorker = idWorker;
        this.userMapper = userMapper;
        this.redisTemplate = redisTemplate;
        this.rabbitTemplate = rabbitTemplate;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<User> findAll() {
        return userDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return Page<User>
     */
    public Page<User> findSearch(Map whereMap, int page, int size) {
        Specification<User> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userDao.findAll(specification, pageRequest);
    }

    public void add(User user) {
        user.setId(String.valueOf(idWorker.nextId()));
        userDao.save(user);
    }

    /**
     * 条件查询
     *
     * @param whereMap 查询的条件
     * @return List<User>
     */
    public List<User> findSearch(Map whereMap) {
        Specification<User> specification = createSpecification(whereMap);
        return userDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return User
     */
    @Cacheable(value = "User", key = "#id")
    public User findById(String id) {
        return userDao.findOneById(id);
    }

    /**
     * 修改
     *
     * @param user user
     */
    public void update(User user) {
        userDao.save(user);
    }

    /**
     * 删除
     *
     * @param id id
     */
    @CacheEvict(value = "User", key = "#id")
    public void deleteById(String id) {
        userDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap 动态条件
     * @return Specification<User>
     */
    private Specification<User> createSpecification(Map searchMap) {

        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 手机号码
            if (searchMap.get("mobile") != null && !"".equals(searchMap.get("mobile"))) {
                predicateList.add(cb.like(root.get("mobile").as(String.class), "%" + searchMap.get("mobile") + "%"));
            }
            // 密码
            if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                predicateList.add(cb.like(root.get("password").as(String.class), "%" + searchMap.get("password") + "%"));
            }
            // 昵称
            if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + searchMap.get("nickname") + "%"));
            }
            // 性别
            if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
                predicateList.add(cb.like(root.get("sex").as(String.class), "%" + searchMap.get("sex") + "%"));
            }
            // 头像
            if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar"))) {
                predicateList.add(cb.like(root.get("avatar").as(String.class), "%" + searchMap.get("avatar") + "%"));
            }
            // E-Mail
            if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
                predicateList.add(cb.like(root.get("email").as(String.class), "%" + searchMap.get("email") + "%"));
            }
            // 兴趣
            if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
                predicateList.add(cb.like(root.get("interest").as(String.class), "%" + searchMap.get("interest") + "%"));
            }
            // 个性
            if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
                predicateList.add(cb.like(root.get("personality").as(String.class), "%" + searchMap.get("personality") + "%"));
            }

            int size = predicateList.size();
            return cb.and(predicateList.toArray(new Predicate[size]));

        };

    }

    /**
     * 登录
     *
     * @param user
     * @return
     */
    public User login(User user) {
        User loginUser = userDao.findUserByMobile(user.getMobile());
        if (null != loginUser && bCryptPasswordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
            return loginUser;
        }

        return null;
    }

    /**
     * 注册
     *
     * @param user
     */
    public void registerUser(User user, String code) {
        if (!StringUtils.equals(code, redisTemplate.opsForValue().get(user.getMobile()))) {
            throw new RuntimeException("验证码错误，请重新发送验证码");
        }
        user.setId(String.valueOf(idWorker.nextId()));

        user.setPassword(encoderPwd(user.getPassword()));

        user.setFollowcount(0);
        user.setFanscount(0);
        user.setOnline(0L);
        user.setRegdate(new Date());
        user.setUpdatedate(new Date());
        user.setLastdate(new Date());


        userDao.save(user);
        redisTemplate.delete(user.getMobile());
    }

    /**
     * 修改当前登录用户的信息
     *
     * @param user
     */
    public void saveinfo(User user) {
        userDao.save(user);
    }

    /**
     * 关注某用户
     *
     * @param id
     * @param userId
     */
    public void updadeFollow(String id, String userId) {
        userMapper.insertFollow(id, userId);
    }

    /**
     * 删除关注的某用户
     *
     * @param id
     * @param userId
     */
    public void deleteFollow(String id, String userId) {
        userMapper.deleteFollow(id, userId);
    }


    /**
     * 查询我的粉丝
     *
     * @param userid
     * @return
     */
    public List<User> followMyFans(String userid) {
        List<String> followIdList = userMapper.followMyFans(userid);
        return this.getFollowUsers(followIdList);
    }


    /**
     * 查询我的关注
     *
     * @param userid
     * @return
     */
    public List<User> followMyFollow(String userid) {
        List<String> followIdList = userMapper.followMyFollowId(userid);
        return this.getFollowUsers(followIdList);
    }


    /**
     * 查询我的关注ID列表
     *
     * @param userid
     * @return
     */
    public List<String> followMyFollowId(String userid) {
        return userMapper.followMyFollowId(userid);
    }


    private List<User> getFollowUsers(List<String> followIdList) {
        if (null != followIdList) {
            List<User> list = new ArrayList<>();
            for (String id : followIdList) {
                User user = userDao.findOneById(id);
                user.setPassword("");
                list.add(user);
            }
            return list;
        }
        return null;
    }

    public void sendsms(String mobile) {
        if (StringUtils.isNotEmpty(mobile)) {
            if (null != userDao.findUserByMobile(mobile)) {
                throw new RuntimeException("手机号已存在，请登录！");
            }
            String mobileCode = RandomUtil.randomNumbers(6);
            //放进redis里并设置过期时间
            redisTemplate.opsForValue().set(mobile, mobileCode, 5, TimeUnit.MINUTES);
            Map<String, String> map = new HashMap<>();
            map.put("mobile", mobile);
            map.put("mobileCode", mobileCode);
            //发送队列的消息
            rabbitTemplate.convertAndSend("sms", map);
        }
    }

    private String encoderPwd(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public void incFollowCount(String userId, Integer num) {
        userMapper.incFollowCount(userId, num);
    }

    public void incFansCount(String userId, Integer num) {
        userMapper.incFansCount(userId, num);
    }
}
