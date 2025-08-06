package com.example.StainlesSteel.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.StainlesSteel.exceptions.FileStorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

    private final Cloudinary cloudinary;

    public String storeFile(MultipartFile file) {
        try {
            String uniqueFileName = UUID.randomUUID().toString();
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "public_id", "uploads/" + uniqueFileName,
                    "resource_type", "auto" // handles all types (images, videos, etc.)
            ));

            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            log.error("Cloudinary upload failed", e);
            throw new FileStorageException("Could not upload file to Cloudinary", e);
        }
    }
}
