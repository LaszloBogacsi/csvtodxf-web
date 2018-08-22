package com.laszlobogacsi.csvtodxfwebservice.resources;

import com.laszlobogacsi.csvtodxfwebservice.DrawingConfig;
import com.laszlobogacsi.csvtodxfwebservice.StorageService;
import com.laszlobogacsi.csvtodxfwebservice.convertjob.ConvertJob;
import com.laszlobogacsi.csvtodxfwebservice.convertjob.JobManager;
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

        JobResponse response = new JobResponse(manager.execute(new ConvertJob(config.getDrawingId().toString(), config)));

        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"response\":\"" + response + "\"}"); // return a job id
        return responseEntity;
    }

    @RequestMapping("/downloadlink/{id}") // this is should be the job id
    @GetMapping
    ResponseEntity getDownloadLink(@PathVariable String id) {

        System.out.println("id = " + id);
        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"id\":\"" + id + "\"}");
        return response;
    }

}
