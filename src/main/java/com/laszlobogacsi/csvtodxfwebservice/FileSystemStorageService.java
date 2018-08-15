package com.laszlobogacsi.csvtodxfwebservice;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.UUID;

@Component("fileStorageService")
public class FileSystemStorageService implements StorageService {

    @Override
    public UUID store(MultipartFile file) {
        // save incoming file to a folder
            // create a folder if not exist
        // get path to the folder
        // if file saved store the path and an id for the file.

        //return UUID.randomUUID();
        throw new NotImplementedException();
    }
}
