package com.tbi.webservices.service;

import com.tbi.webservices.dto.DistributionBody;
import com.tbi.webservices.payload.response.FileResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class FileUploadService {

    @Value("${tbi.file.path}")
    private String filePath;

    public FileResponse fileUpload(MultipartFile file, String distributionBody) {
        try {
            File path = new File(filePath + file.getOriginalFilename());
            path.createNewFile();
            FileOutputStream output = new FileOutputStream(path);
            output.write(file.getBytes());
            output.close();
            return FileResponse.builder().
                    distributionBody(distributionBody).
                    message("File uploaded successfully.").
                    fileName(file.getOriginalFilename())
                    .build();

        } catch (Exception e) {
            return FileResponse.builder()
                    .message(e.toString())
                    .build();
        }
    }
}
