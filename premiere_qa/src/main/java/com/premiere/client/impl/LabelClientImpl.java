package com.premiere.client.impl;

import com.premiere.client.LabelClient;
import com.premiere.common.Result;
import com.premiere.common.StatusCode;
import org.springframework.stereotype.Component;

@Component
public class LabelClientImpl implements LabelClient {
    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.ERROR,"熔断器启动了");
    }
}
