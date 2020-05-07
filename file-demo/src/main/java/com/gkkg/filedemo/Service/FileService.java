package com.gkkg.filedemo.Service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    //method to upload files
    Boolean save(MultipartFile file, String fileName);
    //method to retrieve files
    byte[] load(String fileName, String filePath);
}
