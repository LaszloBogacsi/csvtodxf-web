package com.laszlobogacsi.csvtodxfwebservice;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    public void store(MultipartFile file, String destination);
}
