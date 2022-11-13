package com.project.webservices.api;


import com.project.webservices.service.FileUploadService;
import com.project.webservices.payload.response.FileResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/file-service")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping(value = "/uploadFile")
    public ResponseEntity<FileResponse> fileUpload(@RequestParam(name = "file") MultipartFile file, @RequestParam(name="distribution") String distributionBody){
            return new ResponseEntity<>(fileUploadService.createRequestBody(file,distributionBody),HttpStatus.OK);
    }

}
