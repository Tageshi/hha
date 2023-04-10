package com.bin.common.core.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author tageshi
 * @date 2023/4/10 1:46
 */
public class ImageUtils {
    public static String createGroupImg(String groupName,Long userId) throws IOException {
        char firstChar = groupName.charAt(0);

        BufferedImage image = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.decode("#26A69A"));
        graphics.fillRect(0, 0, 250, 250);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("微软雅黑", Font.BOLD, 150));
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(String.valueOf(firstChar));
        int stringHeight = fontMetrics.getHeight();
        graphics.drawString(String.valueOf(firstChar), (250 - stringWidth) / 2, (250 - stringHeight) / 2 + fontMetrics.getAscent());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(image).size(250, 250).outputFormat("png").toOutputStream(outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration();
        cfg.useHttpsDomains = false;
        UploadManager uploadManager = new UploadManager(cfg);

        String key = "群头像库/" + groupName + "_user" + userId.toString() +".png"; // 设置上传到群头像库目录下，并以文本首字符命名文件
        String upToken = QiNiuUtils.getUploadTokenToGroupImg(key);
        try {
            Response response = uploadManager.put(imageBytes, key, upToken);
            return "http://rse3hedfs.hd-bkt.clouddn.com/"+response.jsonToMap().get("key");
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
        return null;
    }
}
