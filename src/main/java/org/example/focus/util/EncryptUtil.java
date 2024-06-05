package org.example.focus.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptUtil {
    public static String imageSaveUrl;

    public static String imageAccessUrl;

    @Value(value = "${image_save_url}")
    public void setImageSaveUrl(String imageSaveUrl) {
        this.imageSaveUrl = imageSaveUrl;
    }

    @Value(value = "${image_access_url}")
    public void setImageAccessUrl(String imageAccessUrl) {
        this.imageAccessUrl = imageAccessUrl;
    }
}

