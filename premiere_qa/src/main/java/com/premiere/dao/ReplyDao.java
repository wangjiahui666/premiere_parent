package com.premiere.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.premiere.pojo.Reply;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ReplyDao extends JpaRepository<Reply,String>,JpaSpecificationExecutor<Reply>{
	Reply findOneById(String id);

	List<Reply> findReplyByProblemid(String problemid);
}
