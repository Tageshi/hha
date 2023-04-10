package com.bin.common.core.utils;

/**
 * @author tageshi
 * @date 2023/4/11 0:27
 */
public class QuartzUtils {
    /**
     * @description: 计算cron表达式
     * @author: tageshi
     * @date: 2023/4/11 1:01
     **/
    public static String calculateCronExpression(String unit, int number) {
        String cronExpression;
        switch (unit) {
            case "周":
                cronExpression = "0 0 0 ? * " + "MON-SUN/" + number;
                break;
            case "月":
                cronExpression = "0 0 0 " + number + " * ?";
                break;
            default:
                throw new IllegalArgumentException("Invalid unit: " + unit);
        }
        return cronExpression;
    }
}
