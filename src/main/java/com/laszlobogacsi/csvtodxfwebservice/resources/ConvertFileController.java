package com.laszlobogacsi.csvtodxfwebservice.resources;

import com.laszlobogacsi.csvtodxfwebservice.DrawingConfig;
import com.laszlobogacsi.csvtodxfwebservice.StorageService;
import com.laszlobogacsi.csvtodxfwebservice.convertjob.ConvertJob;
import com.laszlobogacsi.csvtodxfwebservice.convertjob.JobManager;
import com.laszlobogacsi.csvtodxfwebservice.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ConvertFileController {

    @Autowired
    @Qualifier("fileStorageService")
    private StorageService fileStorageService;

    @Autowired
    private JobManager manager;

    @RequestMapping("/convert")
    @PostMapping
    ResponseEntity convert(@RequestBody DrawingConfig config) {

        JobResponse response = manager.start(new ConvertJob(config.getDrawingId().toString(), config));
        String jsonResponse = JsonMapper.fromObj(response);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"response\":" + jsonResponse + "}");
    }

    @RequestMapping("/downloadlink/{id}") // this is should be the job id
    @GetMapping
    ResponseEntity getDownloadLink(@PathVariable String id) {

        System.out.println("id = " + id);
        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"id\":\"" + id + "\"}");
        return response;
    }

}
