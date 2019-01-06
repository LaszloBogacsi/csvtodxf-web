package com.laszlobogacsi.csvtodxfwebservice.file;

import java.util.List;

public interface FileReader {
    List<CsvLine> readLine(String path, String separator);
}
