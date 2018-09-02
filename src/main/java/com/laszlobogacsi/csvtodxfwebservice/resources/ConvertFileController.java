package com.laszlobogacsi.csvtodxfwebservice.resources;

import com.laszlobogacsi.csvtodxfwebservice.DrawingConfig;
import com.laszlobogacsi.csvtodxfwebservice.StorageService;
import com.laszlobogacsi.csvtodxfwebservice.convertjob.ConvertJob;
import com.laszlobogacsi.csvtodxfwebservice.convertjob.JobManager;
import com.laszlobogacsi.csvtodxfwebservice.persistance.model.ConvertTask;
import com.laszlobogacsi.csvtodxfwebservice.persistance.repository.ConvertTaskRepository;
import com.laszlobogacsi.csvtodxfwebservice.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@RestController
public class ConvertFileController {

    @Autowired
    @Qualifier("fileStorageService")
    private StorageService fileStorageService;

    @Autowired
    private JobManager manager;

    @Autowired
    private ConvertTaskRepository convertTaskRepository;

    @CrossOrigin(origins = "http://evil.com/")
    @RequestMapping("/convert")
    @PostMapping
    ResponseEntity convert(@RequestBody DrawingConfig config) {
        System.out.println("called");

        String jsonResponse = null;
        try {
            JobResponse response = manager.start(new ConvertJob(config.getDrawingId().toString(), config));
            ConvertTaskResult result = ConvertTaskResult.builder()
                    .downloadId(response.getDownloadId())
                    .durationInMillies(response.getReport().getDurationInMillies())
                    .fileSize(response.getReport().getFileSize())
                    .numberOfLinesConverted(response.getReport().getNumberOfLinesConverted())
                    .build();
            jsonResponse = JsonMapper.fromObj(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"response\":\"" + Arrays.toString(e.getStackTrace()) + "\"}");
        }

        return ResponseEntity
                .status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"response\":" + jsonResponse + "}");
    }

    @RequestMapping("/download/{id}") // this is should be the job id
    @GetMapping
    ResponseEntity download(@PathVariable String id) {
        ConvertTask convertTask = convertTaskRepository.findByDownloadId(id).get();
        Resource resource = fileStorageService.loadFileAsResource(convertTask.getDownloadPath());
        System.out.println("id = " + id);
        System.out.println("convertTask = " + convertTask.getDownloadPath());
        ResponseEntity response = ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
        return response;
    }

}
