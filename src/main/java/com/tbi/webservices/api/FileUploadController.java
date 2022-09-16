package com.tbi.webservices.api;


import com.tbi.webservices.payload.response.GetFileResponse;
import com.tbi.webservices.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("tbitask")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @Value("${tbi.csv.approved.path}")
    private String path;


    @PostMapping(value = "/uploadFile")
    public ResponseEntity<?> fileUpload(@RequestParam(name = "file") MultipartFile file, @RequestParam(name="distribution") String distributionBody){
            return new ResponseEntity<>(fileUploadService.createRequestBody(file,distributionBody),HttpStatus.OK);
    }

}
