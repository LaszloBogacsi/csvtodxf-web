package com.laszlobogacsi.csvtodxfwebservice.file;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPOutputStream;

@Service
public class GZipFileCompressorService implements FileCompressorService {
    @Override
    public void compress(String pathToFileToCompress, String newFilePath) {
        try(FileOutputStream outputStream = new FileOutputStream(newFilePath);
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream)) {

            byte[] data = Files.readAllBytes(Paths.get(pathToFileToCompress));
            gzipOutputStream.write(data);
        } catch (FileNotFoundException e) {
            // TODO; log a level up and handle exception
        } catch (IOException ioe) {
            // TODO; log a level up and handle exception
        }
    }
}
