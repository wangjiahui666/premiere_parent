package com.premiere.client;

import com.premiere.client.impl.LabelClientImpl;
import com.premiere.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "premiere-base",fallback = LabelClientImpl.class)
public interface LabelClient {

    @RequestMapping(value = "/label/{id}", method = RequestMethod.GET)
    Result findById(@PathVariable String id);
}
