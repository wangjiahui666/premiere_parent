package com.premiere;


import org.apache.http.HttpResponse;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsSend {

    @Value("${api.send.appCode}")
    private String appCode;

    @Value("${api.send.tplId}")
    private String tplId;

    @RabbitHandler
    public void sendSmsCode(Map<String, String> map) {
        sendCode(tplId,appCode,map.get("mobile"),map.get("mobileCode"));
    }

    private void sendCode(String tplId,String appCode, String mobile, String mobileCode) {
        String host = "http://yzx.market.alicloudapi.com";
        String path = "/yzx/sendSms";
        String method = "POST";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + appCode);
        Map<String, String> querys = new HashMap<>();
        querys.put("mobile", mobile);
        querys.put("param", "code:" + mobileCode);
        //querys.put("tpl_id", "TP1710262");
        querys.put("tpl_id", tplId);
        Map<String, String> bodys = new HashMap<>();
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
