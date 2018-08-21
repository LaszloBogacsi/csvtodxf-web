package com.laszlobogacsi.csvtodxfwebservice;

import java.io.File;

public class FileTest {

    public boolean isFileExists(String path) {
        return new File(path).exists();
    }
    public File[] getFiles(String path) {
       return new File(path).listFiles();
    }

    public String[] getDirectoriesList(String path) {
        return new File(path).list();
    }

    public boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}
