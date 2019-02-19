package com.laszlobogacsi.csvtodxfwebservice.resources;

import com.laszlobogacsi.csvtodxfwebservice.StorageService;
import com.laszlobogacsi.csvtodxfwebservice.file.PathProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
public class UploadFileController {
    @Autowired
    @Qualifier("fileStorageService")
    private StorageService fileStorageService;

    @Autowired
    @Qualifier("fileSystemPathProvider")
    private PathProvider pathProvider;

    @CrossOrigin
    @RequestMapping("/upload-file")
    @PostMapping
    ResponseEntity uploadFile(@ModelAttribute("file") MultipartFile file) {
        UUID uniqueFolderName = UUID.randomUUID();
        fileStorageService.store(file, pathProvider.getPathForParentFolderBy(uniqueFolderName.toString()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"id\":\"" + uniqueFolderName + "\", \"fileName\":\""+ file.getOriginalFilename()+"\"}");
    }
}
