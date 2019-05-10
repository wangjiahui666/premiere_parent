package com.premiere.service;

import com.premiere.common.IdWorker;
import com.premiere.dao.LabelDao;
import com.premiere.mapper.LabelMapper;
import com.premiere.pojo.Label;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@CacheConfig(cacheNames = "label")
public class LabelService {

    private final LabelMapper labelMapper;
    private final LabelDao labelDao;
    private final IdWorker idWorker;

    @Autowired
    public LabelService(LabelMapper labelMapper, LabelDao labelDao, IdWorker idWorker) {
        this.labelMapper = labelMapper;
        this.labelDao = labelDao;
        this.idWorker = idWorker;
    }

    public void save(Label label) {
        if (StringUtils.isEmpty(label.getId())) {
            label.setId(String.valueOf(idWorker.nextId()));
        }
        labelDao.save(label);
    }

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    @Cacheable(value = "Label", key = "#id")
    public Label findLabelById(String id) {
        return labelDao.findLabelById(id);
    }

    @CacheEvict(value = "Label", key = "#id")
    public void deleteLabelById(String id) {
        labelMapper.deleteByPrimaryKey(id);
    }

    public Page<Label> search(Label label, Integer page, Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        return labelDao.findAll(this.getLabelSpecification(label), request);
    }

    public List<Label> findTopList(String recommend) {
        return labelDao.findAllByRecommendOrderByFansDesc(recommend);
    }

    public List<Label> findByStateAllList(String state) {
        return labelDao.findAllByStateOrderByFansDesc(state);
    }

    public List<Label> searchByLabel(Label label) {
        return labelDao.findAll(this.getLabelSpecification(label));
    }

    private Specification<Label> getLabelSpecification(Label label) {
        return (Specification<Label>) (root, criteriaQuery, cb) -> {
            List<Predicate> pc = new ArrayList<>();
            if (null == label) return null;

            if (StringUtils.isNotEmpty(label.getLabelname())) {
                pc.add(cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%"));
            }
            if (StringUtils.isNotEmpty(label.getId())) {
                pc.add(cb.equal(root.get("id").as(String.class), label.getId()));
            }
            if (null != label.getCount()) {
                pc.add(cb.equal(root.get("count").as(Integer.class), label.getCount()));
            }
            if (null != label.getFans()) {
                pc.add(cb.equal(root.get("fans").as(Integer.class), label.getFans()));
            }
            if (StringUtils.isNotEmpty(label.getRecommend())) {
                pc.add(cb.equal(root.get("recommend").as(String.class), label.getRecommend()));
            }
            if (StringUtils.isNotEmpty(label.getState())) {
                pc.add(cb.equal(root.get("state").as(String.class), label.getState()));
            }

            if (pc.size() == 0) return null;
            int size = pc.size();
            return cb.and(pc.toArray(new Predicate[size]));
        };
    }
}
