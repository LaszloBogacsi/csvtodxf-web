package com.laszlobogacsi.csvtodxfwebservice.converter;

import com.laszlobogacsi.csvtodxfwebservice.DrawingConfig;
import com.laszlobogacsi.csvtodxfwebservice.converter.dxf.DXF;
import com.laszlobogacsi.csvtodxfwebservice.file.CsvLine;
import com.laszlobogacsi.csvtodxfwebservice.file.FileReader;
import com.laszlobogacsi.csvtodxfwebservice.file.PathProvider;
import com.laszlobogacsi.csvtodxfwebservice.report.ConversionReport;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvToDxfConverter implements Converter {
    private PathProvider pathProvider;
    private FileReader reader;

    public CsvToDxfConverter(FileReader fileReader, PathProvider pathProvider) {
        this.reader = fileReader;
        this.pathProvider = pathProvider;
    }

    @Override
    public ConversionReport convert(DrawingConfig config) throws Exception {
        long start = System.currentTimeMillis();
        List<CsvLine> csvLines = readLines(config);
        String dxf = new DXF(config).createDxf(csvLines);
        String outputPath = pathProvider.getPathForFileBy(config.getDrawingId().toString(), getDxfFileName(config.getFileName()));
        double fileSize = saveToFile(dxf, outputPath);
        long duration = System.currentTimeMillis() - start;

        return ConversionReport.builder()
                .savedFilePath(outputPath)
                .durationInMillies(duration)
                .fileSize(fileSize)
                .numberOfLinesConverted(csvLines.size())
                .build();
    }

    private List<CsvLine> readLines(DrawingConfig config) {
        String inputPath = pathProvider.getPathForFileBy(config.getDrawingId().toString(), config.getFileName());
        return this.reader.readLine(inputPath, config.getSeparator());
    }

    private double saveToFile(String dxf, String outputPath) throws IOException { // should go to it's own fileWriter
        Path newFile = Files.write(Paths.get(outputPath), dxf.getBytes());
        File file = new File(String.valueOf(newFile));
        return file.length();
    }

    private String getDxfFileName(String originalFileName) {
        return originalFileName.replaceAll("\\.\\w+$", ".dxf");
    }
}
