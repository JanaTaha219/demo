package com.example.demo.service;

import com.example.demo.reponse.BasicResponce;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {
    public ResponseEntity<Object> downloadFile(String filename);
    public String fileUpload(MultipartFile file) throws IOException;
}
