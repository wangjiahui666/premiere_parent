package com.premiere.client;

import com.premiere.client.impl.UserClientImpl;
import com.premiere.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "premiere-user", fallback = UserClientImpl.class)
public interface UserClient {

    @PutMapping(value = "/user/incfollow/{userId}/{num}")
    Result incFollowCount(@PathVariable String userId, @PathVariable Integer num);

    @PutMapping(value = "/user/incFansCount/{userId}/{num}")
    Result incFansCount(@PathVariable String userId, @PathVariable Integer num);
}
