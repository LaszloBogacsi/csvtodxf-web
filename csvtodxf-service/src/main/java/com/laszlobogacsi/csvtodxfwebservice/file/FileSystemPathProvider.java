package com.laszlobogacsi.csvtodxfwebservice.file;

import org.springframework.stereotype.Service;

import java.io.File;

@Service("fileSystemPathProvider")
public class FileSystemPathProvider implements PathProvider {

    private static final String ROOT_PATH = System.getProperty("user.dir");
    private static final String SEPARATOR = File.separator;

    @Override
    public String getPathForParentFolderBy(String folderId) {
        return ROOT_PATH + SEPARATOR + "file-uploads" + SEPARATOR + folderId;    }

    @Override
    public String getPathForFileBy(String id, String fileName) {
        return getPathForParentFolderBy(id) + SEPARATOR + fileName;
    }
}
