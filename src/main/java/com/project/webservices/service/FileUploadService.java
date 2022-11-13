package com.project.webservices.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.webservices.dto.DistributionBody;
import com.project.webservices.payload.request.DistributionStatus;
import com.project.webservices.payload.response.FileResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadService.class);
    @Value("${project.csv.callback.path}")
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
                DistributionStatus.SUCCESS.name().equalsIgnoreCase(distributionBodyObject.getStatus())){
            try {
                String fileName = createFileName(distributionBodyObject.getDistributionId());
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
            LOGGER.error("Error {}", e);
        }
        return distributionBody;
    }

    public boolean checkDistributionStatus(DistributionBody distributionBodyObject) {
        if (DistributionStatus.FAIL.name().equalsIgnoreCase(distributionBodyObject.getStatus())
                || DistributionStatus.SUCCESS.name().equalsIgnoreCase(distributionBodyObject.getStatus())) {
            return true;
        }
        return false;
    }

    public String createFileName(Long distributionId){
        return distributionId+".csv";
    }

}
