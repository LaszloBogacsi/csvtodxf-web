package com.laszlobogacsi.csvtodxfwebservice;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component("fileStorageService")
public class FileSystemStorageService implements StorageService {

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
