package com.premiere.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProblemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProblemExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createtime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createtime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createtime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createtime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createtime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createtime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createtime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createtime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createtime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createtime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createtime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createtime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updatetime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updatetime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("updatetime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("updatetime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("updatetime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updatetime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("updatetime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("updatetime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("updatetime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("updatetime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("updatetime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("updatetime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUseridIsNull() {
            addCriterion("userid is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("userid is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(String value) {
            addCriterion("userid =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(String value) {
            addCriterion("userid <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(String value) {
            addCriterion("userid >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(String value) {
            addCriterion("userid >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(String value) {
            addCriterion("userid <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(String value) {
            addCriterion("userid <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLike(String value) {
            addCriterion("userid like", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotLike(String value) {
            addCriterion("userid not like", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<String> values) {
            addCriterion("userid in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<String> values) {
            addCriterion("userid not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(String value1, String value2) {
            addCriterion("userid between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(String value1, String value2) {
            addCriterion("userid not between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNull() {
            addCriterion("nickname is null");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNotNull() {
            addCriterion("nickname is not null");
            return (Criteria) this;
        }

        public Criteria andNicknameEqualTo(String value) {
            addCriterion("nickname =", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotEqualTo(String value) {
            addCriterion("nickname <>", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThan(String value) {
            addCriterion("nickname >", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThanOrEqualTo(String value) {
            addCriterion("nickname >=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThan(String value) {
            addCriterion("nickname <", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThanOrEqualTo(String value) {
            addCriterion("nickname <=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLike(String value) {
            addCriterion("nickname like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotLike(String value) {
            addCriterion("nickname not like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameIn(List<String> values) {
            addCriterion("nickname in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotIn(List<String> values) {
            addCriterion("nickname not in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameBetween(String value1, String value2) {
            addCriterion("nickname between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotBetween(String value1, String value2) {
            addCriterion("nickname not between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andVisitsIsNull() {
            addCriterion("visits is null");
            return (Criteria) this;
        }

        public Criteria andVisitsIsNotNull() {
            addCriterion("visits is not null");
            return (Criteria) this;
        }

        public Criteria andVisitsEqualTo(Long value) {
            addCriterion("visits =", value, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsNotEqualTo(Long value) {
            addCriterion("visits <>", value, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsGreaterThan(Long value) {
            addCriterion("visits >", value, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsGreaterThanOrEqualTo(Long value) {
            addCriterion("visits >=", value, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsLessThan(Long value) {
            addCriterion("visits <", value, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsLessThanOrEqualTo(Long value) {
            addCriterion("visits <=", value, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsIn(List<Long> values) {
            addCriterion("visits in", values, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsNotIn(List<Long> values) {
            addCriterion("visits not in", values, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsBetween(Long value1, Long value2) {
            addCriterion("visits between", value1, value2, "visits");
            return (Criteria) this;
        }

        public Criteria andVisitsNotBetween(Long value1, Long value2) {
            addCriterion("visits not between", value1, value2, "visits");
            return (Criteria) this;
        }

        public Criteria andThumbupIsNull() {
            addCriterion("thumbup is null");
            return (Criteria) this;
        }

        public Criteria andThumbupIsNotNull() {
            addCriterion("thumbup is not null");
            return (Criteria) this;
        }

        public Criteria andThumbupEqualTo(Long value) {
            addCriterion("thumbup =", value, "thumbup");
            return (Criteria) this;
        }

        public Criteria andThumbupNotEqualTo(Long value) {
            addCriterion("thumbup <>", value, "thumbup");
            return (Criteria) this;
        }

        public Criteria andThumbupGreaterThan(Long value) {
            addCriterion("thumbup >", value, "thumbup");
            return (Criteria) this;
        }

        public Criteria andThumbupGreaterThanOrEqualTo(Long value) {
            addCriterion("thumbup >=", value, "thumbup");
            return (Criteria) this;
        }

        public Criteria andThumbupLessThan(Long value) {
            addCriterion("thumbup <", value, "thumbup");
            return (Criteria) this;
        }

        public Criteria andThumbupLessThanOrEqualTo(Long value) {
            addCriterion("thumbup <=", value, "thumbup");
            return (Criteria) this;
        }

        public Criteria andThumbupIn(List<Long> values) {
            addCriterion("thumbup in", values, "thumbup");
            return (Criteria) this;
        }

        public Criteria andThumbupNotIn(List<Long> values) {
            addCriterion("thumbup not in", values, "thumbup");
            return (Criteria) this;
        }

        public Criteria andThumbupBetween(Long value1, Long value2) {
            addCriterion("thumbup between", value1, value2, "thumbup");
            return (Criteria) this;
        }

        public Criteria andThumbupNotBetween(Long value1, Long value2) {
            addCriterion("thumbup not between", value1, value2, "thumbup");
            return (Criteria) this;
        }

        public Criteria andReplyIsNull() {
            addCriterion("reply is null");
            return (Criteria) this;
        }

        public Criteria andReplyIsNotNull() {
            addCriterion("reply is not null");
            return (Criteria) this;
        }

        public Criteria andReplyEqualTo(Long value) {
            addCriterion("reply =", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyNotEqualTo(Long value) {
            addCriterion("reply <>", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyGreaterThan(Long value) {
            addCriterion("reply >", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyGreaterThanOrEqualTo(Long value) {
            addCriterion("reply >=", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyLessThan(Long value) {
            addCriterion("reply <", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyLessThanOrEqualTo(Long value) {
            addCriterion("reply <=", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyIn(List<Long> values) {
            addCriterion("reply in", values, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyNotIn(List<Long> values) {
            addCriterion("reply not in", values, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyBetween(Long value1, Long value2) {
            addCriterion("reply between", value1, value2, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyNotBetween(Long value1, Long value2) {
            addCriterion("reply not between", value1, value2, "reply");
            return (Criteria) this;
        }

        public Criteria andSolveIsNull() {
            addCriterion("solve is null");
            return (Criteria) this;
        }

        public Criteria andSolveIsNotNull() {
            addCriterion("solve is not null");
            return (Criteria) this;
        }

        public Criteria andSolveEqualTo(String value) {
            addCriterion("solve =", value, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveNotEqualTo(String value) {
            addCriterion("solve <>", value, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveGreaterThan(String value) {
            addCriterion("solve >", value, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveGreaterThanOrEqualTo(String value) {
            addCriterion("solve >=", value, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveLessThan(String value) {
            addCriterion("solve <", value, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveLessThanOrEqualTo(String value) {
            addCriterion("solve <=", value, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveLike(String value) {
            addCriterion("solve like", value, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveNotLike(String value) {
            addCriterion("solve not like", value, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveIn(List<String> values) {
            addCriterion("solve in", values, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveNotIn(List<String> values) {
            addCriterion("solve not in", values, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveBetween(String value1, String value2) {
            addCriterion("solve between", value1, value2, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveNotBetween(String value1, String value2) {
            addCriterion("solve not between", value1, value2, "solve");
            return (Criteria) this;
        }

        public Criteria andReplynameIsNull() {
            addCriterion("replyname is null");
            return (Criteria) this;
        }

        public Criteria andReplynameIsNotNull() {
            addCriterion("replyname is not null");
            return (Criteria) this;
        }

        public Criteria andReplynameEqualTo(String value) {
            addCriterion("replyname =", value, "replyname");
            return (Criteria) this;
        }

        public Criteria andReplynameNotEqualTo(String value) {
            addCriterion("replyname <>", value, "replyname");
            return (Criteria) this;
        }

        public Criteria andReplynameGreaterThan(String value) {
            addCriterion("replyname >", value, "replyname");
            return (Criteria) this;
        }

        public Criteria andReplynameGreaterThanOrEqualTo(String value) {
            addCriterion("replyname >=", value, "replyname");
            return (Criteria) this;
        }

        public Criteria andReplynameLessThan(String value) {
            addCriterion("replyname <", value, "replyname");
            return (Criteria) this;
        }

        public Criteria andReplynameLessThanOrEqualTo(String value) {
            addCriterion("replyname <=", value, "replyname");
            return (Criteria) this;
        }

        public Criteria andReplynameLike(String value) {
            addCriterion("replyname like", value, "replyname");
            return (Criteria) this;
        }

        public Criteria andReplynameNotLike(String value) {
            addCriterion("replyname not like", value, "replyname");
            return (Criteria) this;
        }

        public Criteria andReplynameIn(List<String> values) {
            addCriterion("replyname in", values, "replyname");
            return (Criteria) this;
        }

        public Criteria andReplynameNotIn(List<String> values) {
            addCriterion("replyname not in", values, "replyname");
            return (Criteria) this;
        }

        public Criteria andReplynameBetween(String value1, String value2) {
            addCriterion("replyname between", value1, value2, "replyname");
            return (Criteria) this;
        }

        public Criteria andReplynameNotBetween(String value1, String value2) {
            addCriterion("replyname not between", value1, value2, "replyname");
            return (Criteria) this;
        }

        public Criteria andReplytimeIsNull() {
            addCriterion("replytime is null");
            return (Criteria) this;
        }

        public Criteria andReplytimeIsNotNull() {
            addCriterion("replytime is not null");
            return (Criteria) this;
        }

        public Criteria andReplytimeEqualTo(Date value) {
            addCriterion("replytime =", value, "replytime");
            return (Criteria) this;
        }

        public Criteria andReplytimeNotEqualTo(Date value) {
            addCriterion("replytime <>", value, "replytime");
            return (Criteria) this;
        }

        public Criteria andReplytimeGreaterThan(Date value) {
            addCriterion("replytime >", value, "replytime");
            return (Criteria) this;
        }

        public Criteria andReplytimeGreaterThanOrEqualTo(Date value) {
            addCriterion("replytime >=", value, "replytime");
            return (Criteria) this;
        }

        public Criteria andReplytimeLessThan(Date value) {
            addCriterion("replytime <", value, "replytime");
            return (Criteria) this;
        }

        public Criteria andReplytimeLessThanOrEqualTo(Date value) {
            addCriterion("replytime <=", value, "replytime");
            return (Criteria) this;
        }

        public Criteria andReplytimeIn(List<Date> values) {
            addCriterion("replytime in", values, "replytime");
            return (Criteria) this;
        }

        public Criteria andReplytimeNotIn(List<Date> values) {
            addCriterion("replytime not in", values, "replytime");
            return (Criteria) this;
        }

        public Criteria andReplytimeBetween(Date value1, Date value2) {
            addCriterion("replytime between", value1, value2, "replytime");
            return (Criteria) this;
        }

        public Criteria andReplytimeNotBetween(Date value1, Date value2) {
            addCriterion("replytime not between", value1, value2, "replytime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}