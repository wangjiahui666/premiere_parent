package com.premiere.dao;

import com.premiere.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LabelDao extends JpaRepository<Label,String>, JpaSpecificationExecutor<Label> {

    Label findLabelById(String id);

    List<Label> findAllByRecommendOrderByFansDesc(String recommend);

    /**
     * 有效标签列表
     * @param state 状态
     * @return 标签列表
     */
    List<Label> findAllByStateOrderByFansDesc(String state);
}
