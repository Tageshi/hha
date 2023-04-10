package com.bin.common.core.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author tageshi
 * @date 2023/4/9 20:06
 */
public class QiNiuUtils {
    private static String accessKey = "NW8p33-Rgps_UEY9A_XXcfOmx4AYYYPbpTd-jGWD";
    private static String secretKey = "rfNXpiQyrU-ctEzxYz6cecWk4kCfQxHfT3UbKQHW";
    private static String bucket = "fwwb037";
    public static String domain = "http://rse3hedfs.hd-bkt.clouddn.com";

    private static UploadManager uploadManager;

    @PostConstruct
    public void init() {
        Configuration cfg = new Configuration();
        uploadManager = new UploadManager(cfg);

    }
    public static String getUploadToken() {
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucket);
    }
    public static String getUploadTokenToGroupImg(String key) {
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("saveKey", key);
        return auth.uploadToken(bucket, null, 3600, putPolicy);
    }


}
