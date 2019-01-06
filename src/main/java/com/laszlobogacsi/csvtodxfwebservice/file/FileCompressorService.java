package com.laszlobogacsi.csvtodxfwebservice.file;

import java.io.File;

public interface FileCompressorService {
    public void compress(String pathToFileToCompress, String newFilePath);
}
