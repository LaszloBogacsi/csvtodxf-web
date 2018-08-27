package com.laszlobogacsi.csvtodxfwebservice;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    public void store(MultipartFile file, String destination);
    Resource loadFileAsResource(String fileName);
}
