package com.laszlobogacsi.csvtodxfwebservice.resources;

import com.laszlobogacsi.csvtodxfwebservice.StorageService;
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

@RestController
public class UploadFileRestController {
    @Autowired
    @Qualifier("fileStorageService")
    private StorageService fileStorageService;

    @RequestMapping("/upload-file")
    @PostMapping
    ResponseEntity uploadFile(@ModelAttribute("file") MultipartFile file) {
        fileStorageService.store(file);
//        FileuploadResponse body = new FileuploadResponse(); // return an id for the saved file, this needs to be kep in a table id, filename, location.
        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"id\":\"123456789\"}");
//        ResponseEntity.badRequest()...
        return response;
    }

    // FileuploadREsponse is an object returned form the post request, serialized in to json, attribs: id of the saved file, optional (date, time, filesize, filename)
}
