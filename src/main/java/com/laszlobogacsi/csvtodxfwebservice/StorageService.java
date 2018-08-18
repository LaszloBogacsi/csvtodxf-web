package com.laszlobogacsi.csvtodxfwebservice;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface StorageService {
    public void store(MultipartFile file, String destination);
}
