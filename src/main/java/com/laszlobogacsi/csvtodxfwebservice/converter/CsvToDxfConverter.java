package com.laszlobogacsi.csvtodxfwebservice.converter;

import com.laszlobogacsi.csvtodxfwebservice.DrawingConfig;
import com.laszlobogacsi.csvtodxfwebservice.converter.dxf.DXF;
import com.laszlobogacsi.csvtodxfwebservice.file.CsvLine;
import com.laszlobogacsi.csvtodxfwebservice.file.FileReader;
import com.laszlobogacsi.csvtodxfwebservice.file.PathProvider;
import com.laszlobogacsi.csvtodxfwebservice.report.ConversionReport;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class CsvToDxfConverter implements Converter {
    private DrawingConfig config;
    private ConversionReport report;
    private PathProvider pathProvider;
    private FileReader reader;

    public CsvToDxfConverter(FileReader fileReader, ConversionReport report, PathProvider pathProvider) {
        this.reader = fileReader;
        this.report = report;
        this.pathProvider = pathProvider;
    }

    @Override
    public void convert(DrawingConfig config) throws Exception {
        long start = System.currentTimeMillis();
        this.config = config;
        String dxf = new DXF(config).createDxf(readLines());
        String outputPath = pathProvider.getPathForFileBy(config.getDrawingId().toString(), getDxfFileName(config.getFileName()));
        saveToFile(dxf, outputPath);
        long duration = System.currentTimeMillis() - start;
        this.report.setDurationInMillies(duration);
    }

    private List<CsvLine> readLines() throws IOException {
        String inputPath = pathProvider.getPathForFileBy(config.getDrawingId().toString(), config.getFileName());
        List<CsvLine> lines = this.reader.readLine(inputPath, config.getSeparator());
        this.report.setNumberOfLinesConverted(lines.size());
        return lines;
    }

    private void saveToFile(String dxf, String outputPath) throws IOException {
        Path newFile = Files.write(Paths.get(outputPath), dxf.getBytes());
        File file = new File(String.valueOf(newFile));
        this.report.setFileSize(file.length());
    }

    private String getDxfFileName(String originalFileName) {
        return originalFileName.replaceAll("\\.csv", ".dxf");
    }
}
