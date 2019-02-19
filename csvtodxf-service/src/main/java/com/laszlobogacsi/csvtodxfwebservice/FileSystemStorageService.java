package com.laszlobogacsi.csvtodxfwebservice;

import com.laszlobogacsi.csvtodxfwebservice.configuration.UploadProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service("fileStorageService")
public class FileSystemStorageService implements StorageService {
    private final Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);

    private UploadProperties configuration;

    @Autowired
    public FileSystemStorageService(UploadProperties configuration) {
        this.configuration = configuration;
    }


    @Override
    public void store(MultipartFile multipartFile, String destination) {
        File dir = new File(destination);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = multipartFile.getOriginalFilename();
        File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);

        try {
            multipartFile.transferTo(serverFile);
            logger.info("Storing file to destination: " + destination + " filename: " + fileName);

        } catch (IOException e) {
            e.printStackTrace(); // TODO: handle io errors
            logger.error("Error: " + e.getMessage() + " while storing file to destination: " + destination + " filename: " + fileName);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        return new FileSystemResource(fileName);
    }
}
