package com.bin.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bin.common.core.utils.QiNiuUtils;
import com.bin.user.api.AlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author tageshi
 * @date 2023/4/9 17:40
 */
@Service
@Transactional
public class AlgorithmServiceImpl implements AlgorithmService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * @description: 获取图片数据
     * @author: tageshi
     * @date: 2023/4/9 18:43
     **/
    @Override
    public String identifyPicture(String imageURL){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        //获取七牛云图片url，封装到HTTP请求体中
        requestBody.put("url", imageURL);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        //发送请求，根据算法具体的url
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://124.221.103.248:5050/selectinfo",
                HttpMethod.POST, requestEntity, String.class);

        //获取json格式结果
        return responseEntity.getBody();
    }

    /**
     * @description: 识别包含多张账单的图片
     * @author: tageshi
     * @date: 2023/4/10 13:59
     **/
    @Override
    public String identifyPictures(String imageURL) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        //获取七牛云图片url，封装到HTTP请求体中
        requestBody.put("url", imageURL);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        //发送请求，根据算法具体的url
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://124.221.103.248:5050/selectinfos",
                HttpMethod.POST, requestEntity, String.class);

        //获取json格式结果
        return responseEntity.getBody();
    }


    /**
     * @description: 识别手写账单
     * @author: tageshi
     * @date: 2023/4/10 13:34
     **/
    @Override
    public String identifyHandwriting(String imageURL){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        //获取七牛云图片url，封装到HTTP请求体中
        requestBody.put("url", imageURL);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        //发送请求，根据算法具体的url
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://124.221.103.248:5050/selecthwrinfo",
                HttpMethod.POST, requestEntity, String.class);

        //获取json格式结果
        return responseEntity.getBody();
    }

    /**
     * @description: 识别语音信息
     * @author: tageshi
     * @date: 2023/4/9 18:48
     **/
    @Override
    public String identifyVoiceInfo(String fileURL) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        //获取七牛云图片url，封装到HTTP请求体中
        requestBody.put("url", fileURL);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        //发送请求，根据算法具体的url
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://124.221.103.248:5050/selectyyinfo",
                HttpMethod.POST, requestEntity, String.class);

        //获取json格式结果
        return responseEntity.getBody();
    }

}