package com.tbi.webservices.api;


import com.tbi.webservices.payload.response.FileResponse;
import com.tbi.webservices.payload.response.ResponseMessage;
import com.tbi.webservices.service.FileUploadService;
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
