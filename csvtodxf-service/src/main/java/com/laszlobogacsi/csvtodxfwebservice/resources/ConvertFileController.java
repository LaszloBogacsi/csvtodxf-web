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

import java.util.Optional;
import java.util.UUID;


@RestController
public class ConvertFileController {

    @Autowired
    @Qualifier("fileStorageService")
    private StorageService fileStorageService;

    @Autowired
    private JobManager manager;

    @Autowired
    private ConvertTaskRepository convertTaskRepository;

    @CrossOrigin
    @RequestMapping("/convert")
    @PostMapping
    ResponseEntity convert(@RequestBody DrawingConfig config) {
        String convertJobId = UUID.randomUUID().toString();
        manager.start(new ConvertJob(convertJobId, config));
        return ResponseEntity
                .status(202)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"response\":\"" + convertJobId + "\"}");
    }

    @CrossOrigin
    @RequestMapping("/convert/status/{convertJobId}")
    @GetMapping
    ResponseEntity status(@PathVariable String convertJobId) {
        Optional<ConvertTask> optionalTask = convertTaskRepository.findByJobId(convertJobId);
        ConvertTaskResult result;
        if (optionalTask.isPresent()) {
            ConvertTask task = optionalTask.get();
            result = ConvertTaskResult.builder()
                    .jobResult(task.getResult())
                    .downloadId(task.getDownloadId())
                    .durationInMillies(task.getReport().getDurationInMillies())
                    .fileSize(task.getReport().getFileSize())
                    .numberOfLinesConverted(task.getReport().getNumberOfLinesConverted())
                    .build();
        } else {
            result = ConvertTaskResult.builder().jobResult(JobResult.IN_PROGRESS).build();
        }

        if (result.getJobResult() == JobResult.CONVERSION_ERROR) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result);
        } else {
            return ResponseEntity
                    .status(200)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(JsonMapper.fromObj(result));
        }
    }

    @RequestMapping("/_download/{downloadId}")
    @GetMapping
    ResponseEntity download(@PathVariable String downloadId) {
        ResponseEntity response;
        if (convertTaskRepository.findByDownloadId(downloadId).isPresent()) {
            ConvertTask convertTask = convertTaskRepository.findByDownloadId(downloadId).get();
            Resource resource = fileStorageService.loadFileAsResource(convertTask.getDownloadPath());
            response = ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            response = ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Can not find the requested file");
        }

        return response;
    }

}
