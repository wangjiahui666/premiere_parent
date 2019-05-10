package com.premiere.client.impl;

import com.premiere.client.UserClient;
import com.premiere.common.Result;
import com.premiere.common.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Component
public class UserClientImpl implements UserClient {

    @Override
    public Result incFollowCount(String userId, Integer num) {
        return new Result(false, StatusCode.ERROR, "熔断器启动了");
    }

    @Override
    public Result incFansCount(String userId, Integer num) {
        return new Result(false, StatusCode.ERROR, "熔断器启动了");
    }
}
