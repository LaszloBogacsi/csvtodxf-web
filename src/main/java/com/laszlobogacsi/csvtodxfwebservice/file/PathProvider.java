package com.laszlobogacsi.csvtodxfwebservice.file;

public interface PathProvider {
    public String getPathForParentFolderBy(String id);
    public String getPathForFileBy(String id, String fileName);
}
