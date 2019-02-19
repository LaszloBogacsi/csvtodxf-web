package com.laszlobogacsi.csvtodxfwebservice.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvFileReader implements FileReader {
    private final Logger logger = LoggerFactory.getLogger(CsvFileReader.class);

    @Override
    public List<CsvLine> readLine(String path, final String separator) {
        List<CsvLine> lines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            lines = stream.filter(line -> line.trim().length() != 0)
                    .map(line -> createLine(line, separator))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Error: " + e.getMessage() + " while reading csv line from path: " + path + "with separator: " + separator);
        }
        return lines;
    }

    // TODO: refactor this into it's own linefactory
    private CsvLine createLine(String line, String separator) {
        // trim whitespace
        String[] lineElements = Arrays.stream(line.split(separator)).map(String::trim).toArray(String[]::new);
        // min 3 max 5 args
        switch (lineElements.length) {
            case 3:
                return new CsvLine(lineElements[0], lineElements[1], lineElements[2]);
            case 4:
                return new CsvLine(lineElements[0], lineElements[1], lineElements[2], lineElements[3]);
            case 5:
                return new CsvLine(lineElements[0], lineElements[1], lineElements[2], lineElements[3], lineElements[4]);
            default:
                return new CsvLine(lineElements[0]); // throw exception
        }
    }
}
