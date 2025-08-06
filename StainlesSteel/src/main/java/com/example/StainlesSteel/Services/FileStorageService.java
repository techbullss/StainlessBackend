package com.example.StainlesSteel.Services;

import com.example.StainlesSteel.Configurations.FileStorageProperties;
import com.example.StainlesSteel.exceptions.FileStorageException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {
    private final FileStorageProperties fileStorageProperties;

    private Path fileStorageLocation;

    @PostConstruct
    public void init() {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            log.error("Could not create upload directory", ex);
            throw new FileStorageException("Could not create upload directory", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Filename contains invalid path sequence " + fileName);
            }

            String newFileName = UUID.randomUUID() + "_" + fileName;
            Path targetLocation = fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return "https://stainlessbackend-5.onrender.com/uploads/" + newFileName;
        } catch (IOException ex) {
            log.error("Could not store file {}", fileName, ex);
            throw new FileStorageException("Could not store file " + fileName, ex);
        }
    }
}
