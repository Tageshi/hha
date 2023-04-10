package com.bin.user.api;

import java.io.IOException;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/9 17:39
 */
public interface AlgorithmService {
    public String identifyPicture(String imageURL);
    public String identifyPictures(String imageURL);
    public String identifyHandwriting(String imageURL);
    public String identifyVoiceInfo(String fileURL);
}
