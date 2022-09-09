package com.tbi.webservices.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbi.webservices.dto.DistributionBody;
import com.tbi.webservices.dto.Status;
import com.tbi.webservices.payload.response.FileResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadService.class);
    @Value("${tbi.file.path}")
    private String filePath;

    private final ObjectMapper objectMapper;

    public FileResponse createRequestBody(MultipartFile file, String distributionBody) {

        DistributionBody distributionBodyObject = stringToObject(distributionBody);
        if (distributionBodyObject != null && distributionBodyObject.getDistributionId()!=null) {
            if (checkDistributionStatus(distributionBodyObject)) {
                return fileUpload(file,distributionBodyObject);
            } else {
                return FileResponse.builder()
                        .message("Status of distribution not found")
                        .httpStatus(HttpStatus.BAD_REQUEST).build();
            }
        }
        return FileResponse.builder()
                .message("distributionBody not parse to object. DistributionBody -> "+distributionBody)
                .httpStatus(HttpStatus.BAD_REQUEST).build();

    }
    public FileResponse fileUpload(MultipartFile file,DistributionBody distributionBodyObject){
        if(file.getOriginalFilename().endsWith(".csv") &&
                Status.SUCCESS.name().equalsIgnoreCase(distributionBodyObject.getStatus())){
            try {
                String fileName = createFileName(distributionBodyObject.getDistributionId(),file.getOriginalFilename());
                File path = new File(filePath + fileName);
                path.createNewFile();
                FileOutputStream output = new FileOutputStream(path);
                output.write(file.getBytes());
                output.close();
                return FileResponse.builder().
                        distributionBody(distributionBodyObject)
                        .message("File uploaded successfully.")
                        .fileName(fileName)
                        .httpStatus(HttpStatus.OK)
                        .build();

            } catch (Exception e) {
                return FileResponse.builder()
                        .message(e.toString())
                        .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                        .build();
            }
        }
        return FileResponse.builder().message("File extension must be .csv and distribution status must be success")
                .httpStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
    }

    public DistributionBody stringToObject(String stringDistributionBody) {
        DistributionBody distributionBody = null;
        try {
            distributionBody = objectMapper.readValue(stringDistributionBody, DistributionBody.class);
        } catch (Exception e) {
            LOGGER.error("TbiTaskService : Error {}", e);
        }
        return distributionBody;
    }

    public boolean checkDistributionStatus(DistributionBody distributionBodyObject) {
        if (Status.FAIL.name().equalsIgnoreCase(distributionBodyObject.getStatus())
                || Status.SUCCESS.name().equalsIgnoreCase(distributionBodyObject.getStatus())) {
            return true;
        }
        return false;
    }

    public String createFileName(Long distributionId,String fileName){
        int lastDotIndex = fileName.lastIndexOf('.');
        String csvFile = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss'.csv'").format(new Date());
        String updateDate = csvFile.replaceAll(":","-").replaceAll(" ","-");
        return fileName.substring(0, lastDotIndex )+"_"+distributionId+"_"+updateDate;
    }
}
