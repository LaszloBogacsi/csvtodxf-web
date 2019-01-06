package com.laszlobogacsi.csvtodxfwebservice.file;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service("zipFileCompressorService")
public class ZipFileCompressorService implements FileCompressorService {
    @Override
    public void compress(String sourceFile, String zipName) {

        File targetFile = new File(sourceFile);

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipName))) {

            zipOutputStream.putNextEntry(new ZipEntry(targetFile.getName()));

            byte[] data = Files.readAllBytes(Paths.get(sourceFile));
            zipOutputStream.write(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
