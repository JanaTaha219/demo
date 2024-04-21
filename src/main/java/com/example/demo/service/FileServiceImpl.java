package com.example.demo.service;

import com.example.demo.reponse.BasicResponce;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FileServiceImpl implements FileService{

    public String fileUpload(MultipartFile file) throws IOException {
        File convertFile = new File("images/"+file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
        return file.getBytes().toString();
    }

    public ResponseEntity<Object> downloadFile(String filename){
        try {
            //String filename = "cat.png";
            File file = new File(filename);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();

            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            ResponseEntity<Object>
                    responseEntity = ResponseEntity.ok().headers(headers).contentLength(
                    file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
            return responseEntity;
        }
        catch (Exception e){
            return new ResponseEntity<>(new BasicResponce(400, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
