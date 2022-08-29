package com.tbi.webservices.api;


import com.tbi.webservices.dto.DistributionBody;
import com.tbi.webservices.service.FileUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileController {

    private final FileUploadService fileUploadService;
    @PostMapping(value = "/uploadFile")
    public ResponseEntity<?> fileUpload(@RequestParam(name = "file") MultipartFile file, @RequestParam(name="distribution") String distributionBody){
            return new ResponseEntity<>(fileUploadService.fileUpload(file,distributionBody),HttpStatus.OK);
    }
    
}
