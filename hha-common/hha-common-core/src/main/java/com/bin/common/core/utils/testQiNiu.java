package com.bin.common.core.utils;

import com.qiniu.util.Auth;

/**
 * @author tageshi
 * @date 2023/4/9 21:26
 */
public class testQiNiu {
    private static String accessKey = "NW8p33-Rgps_UEY9A_XXcfOmx4AYYYPbpTd-jGWD";
    private static String secretKey = "rfNXpiQyrU-ctEzxYz6cecWk4kCfQxHfT3UbKQHW";
    private static String bucket = "fwwb037";

    public static void main(String[] args) {

        Auth auth = Auth.create(accessKey, secretKey);
        System.out.println(auth.uploadToken(bucket));
    }
}
