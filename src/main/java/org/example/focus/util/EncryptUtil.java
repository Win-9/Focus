package org.example.focus.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptUtil {
    public static String imageSaveUrl;

    public static String imageAccessUrl;

    public static String imageDeleteUrl;

    public static String namespace;

    @Value(value = "${image_save_url}")
    public void setImageSaveUrl(String imageSaveUrl) {
        this.imageSaveUrl = imageSaveUrl;
    }

    @Value(value = "${image_access_url}")
    public void setImageAccessUrl(String imageAccessUrl) {
        this.imageAccessUrl = imageAccessUrl;
    }

    @Value("${spring.session.redis.namespace}")
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Value(value = "${image_delete_url}")
    public void setImageDelteUrl(String imageDeleteUrl) {
        this.imageDeleteUrl = imageDeleteUrl;
    }
}

