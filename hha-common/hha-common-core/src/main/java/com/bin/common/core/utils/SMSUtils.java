package com.bin.common.core.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * @author tageshi
 * @date 2023/3/15 19:39
 */
public class SMSUtils {
    private static String accessKeyId = "LTAI5tQoyLECrartmYW6hKvs";
    private static String secret = "oxHORKVpJrZZy6Za2hMhXTXptKYjfh";

    /**
     * @description: 短信验证码发送工具类
     * @author: tageshi
     * @date: 2023/3/15 19:40
     **/
    public static boolean sendMessage( String phoneNumbers, String param) {

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", "合合记账");
        request.putQueryParameter("TemplateCode", "SMS_273705838");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + param + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            String json = response.getData();
            Gson g = new Gson();
            HashMap result = g.fromJson(json, HashMap.class);
            if("OK".equals(result.get("Message"))) {
                return true;
            }else{
                System.out.println("短信发送失败，原因："+result.get("Message"));
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;

    }
}
