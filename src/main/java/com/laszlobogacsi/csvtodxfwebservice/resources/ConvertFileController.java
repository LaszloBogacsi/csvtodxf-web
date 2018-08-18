package com.laszlobogacsi.csvtodxfwebservice.resources;

import com.laszlobogacsi.csvtodxfwebservice.DrawingConfig;
import com.laszlobogacsi.csvtodxfwebservice.converter.Converter;
import com.laszlobogacsi.csvtodxfwebservice.converter.CsvToDxfConverter;
import com.laszlobogacsi.csvtodxfwebservice.file.CsvFileReader;
import com.laszlobogacsi.csvtodxfwebservice.file.FileReader;
import com.laszlobogacsi.csvtodxfwebservice.report.ConversionReport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ConvertFileController {

    @RequestMapping("/convert")
    @PostMapping
    ResponseEntity convert(@RequestBody DrawingConfig config) {
        FileReader fileReader = new CsvFileReader();
        ConversionReport conversionReport = new ConversionReport();
        Converter converter = new CsvToDxfConverter(fileReader, conversionReport);
        try {
            converter.convert(config);
        } catch (Exception e) {
            e.printStackTrace(); // TODO: exception handling strategy
        }

        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"config\":\"" + config + "\"}");
        return response;
    }

    @RequestMapping("/downloadlink/{id}")
    @GetMapping
    ResponseEntity getDownloadLink(@PathVariable String id) {

        System.out.println("id = " + id);
        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"id\":\"" + id + "\"}");
        return response;
    }

}
