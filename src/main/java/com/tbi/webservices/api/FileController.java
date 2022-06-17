package com.tbi.webservices.api;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    @Value("${tbi.file.path}")
    private String filePath;
    
    @PostMapping
    public ResponseEntity<Object> fileUpload(@RequestParam("file") MultipartFile file){
        try {
            File path = new File(filePath + file.getOriginalFilename());
            path.createNewFile();
            FileOutputStream output = new FileOutputStream(path);
            output.write(file.getBytes());
            output.close();
            return new ResponseEntity<>("File uploaded successfully.", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
    
}
