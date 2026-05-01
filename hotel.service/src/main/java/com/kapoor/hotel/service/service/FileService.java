package com.kapoor.hotel.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    @Value("${prefix}")
    private String prefix;

    public String uploadFile(String baseDirectory, MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Please upload your file");
        }

        String fileExtension = file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf("."));

        String timeStampFile = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + fileExtension;

        try {
            Path directory = Paths.get(baseDirectory);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            Path targetFile = directory.resolve(timeStampFile);
            Files.copy(
                    file.getInputStream(),
                    targetFile,
                    StandardCopyOption.REPLACE_EXISTING
            );
            return timeStampFile;
        } catch (Exception e) {
            log.error("File upload failed", e);
            throw new RuntimeException("Could not store file");
        }
    }

    public String urlOfFile(String directory, MultipartFile file) {
        String fileName = uploadFile(directory, file);
        return prefix + "uploads/" + fileName;
    }
}