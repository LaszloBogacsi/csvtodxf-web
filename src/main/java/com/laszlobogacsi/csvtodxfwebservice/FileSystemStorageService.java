package com.laszlobogacsi.csvtodxfwebservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service("fileStorageService")
public class FileSystemStorageService implements StorageService {

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
        } catch (IOException e) {
            e.printStackTrace(); // TODO: handle io errors
        }
    }

}
