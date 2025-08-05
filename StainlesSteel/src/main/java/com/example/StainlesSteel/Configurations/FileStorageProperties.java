package com.example.StainlesSteel.Configurations;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
