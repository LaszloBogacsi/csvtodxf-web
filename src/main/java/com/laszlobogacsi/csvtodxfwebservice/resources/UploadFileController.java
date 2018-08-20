package com.laszlobogacsi.csvtodxfwebservice.resources;

import com.laszlobogacsi.csvtodxfwebservice.StorageService;
import com.laszlobogacsi.csvtodxfwebservice.file.PathProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
public class UploadFileController {
    @Autowired
    @Qualifier("fileStorageService")
    private StorageService fileStorageService;

    @Autowired
    @Qualifier("fileSystemPathProvider")
    private PathProvider pathProvider;

    @RequestMapping("/upload-file")
    @PostMapping
    ResponseEntity uploadFile(@ModelAttribute("file") MultipartFile file) {
        UUID uniqueFolderName = UUID.randomUUID();
        fileStorageService.store(file, pathProvider.getPathForParentFolderBy(uniqueFolderName.toString()));
        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"id\":\"" + uniqueFolderName + "\", \"fileName\":\""+ file.getOriginalFilename()+"\"}");
        return response;
    }
}
