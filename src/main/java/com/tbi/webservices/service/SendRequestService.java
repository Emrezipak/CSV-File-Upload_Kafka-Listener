package com.tbi.webservices.service;

import com.tbi.webservices.config.PropertiesConfig;
import com.tbi.webservices.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
@RequiredArgsConstructor
public class SendRequestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendRequestService.class);
    private static final long fixedFileSize = 30;
    private final PropertiesConfig propertiesConfig;
    private final OkHttpClient okHttpClient;
    private static final String TENANT_WARN_MESSAGE = "It must not exceed 25 characters in TENANT length.";
    @Value("${tbi.csv.approved.path}")
    private String approvedFilePath;

    public ResponseMessage callService(String distributionId) {
        try {
            File file = getFileByDistributionId(distributionId);
            if (file != null) {
                long fileSize = checkFileSize(file.length());
                if (fileSize > fixedFileSize) {
                    LOGGER.warn("TBIBulkDistributionSave : File size must not exceed 30 MB.");
                    return ResponseMessage.builder()
                            .message("File size must not exceed 30 MB.")
                            .status(400).build();
                }

                if (propertiesConfig.getHEADER_TENANT().length()>25){
                    LOGGER.warn(TENANT_WARN_MESSAGE);
                    return ResponseMessage.builder()
                            .message(TENANT_WARN_MESSAGE)
                            .status(400).build();
                }
                Long distributionIdLong = Long.valueOf(distributionId);

                JSONObject jsonObject = new JSONObject().put("distributionId", distributionIdLong);
                LOGGER.debug("TBIBulkDistributionSave : JSON Content {}", jsonObject.toString());

                String headerData = propertiesConfig.getHEADER_TENANT() + distributionId;
                String checksum = encryptedHmac(headerData, propertiesConfig.getHEADER_SECRET_KEY());

                RequestBody requestBody = createRequestBody(file, jsonObject.toString());
                Request request = new Request.Builder()
                        .addHeader("Tenant", propertiesConfig.getHEADER_TENANT())
                        .addHeader("Checksum", checksum)
                        .url(propertiesConfig.getHEADER_URL())
                        .post(requestBody)
                        .build();

                Response response = okHttpClient.newCall(request).execute();
                return ResponseMessage.builder()
                       .status(response.code())
                       .response(response.body().string())
                       .build();
            } else {
                return ResponseMessage.builder().message("File named "+distributionId+".csv not found in the path").status(404).build();
            }

        } catch (Exception e) {
            LOGGER.error(e.toString());
            return ResponseMessage.builder().message(e.toString()).status(500).build();
        }
    }

    public RequestBody createRequestBody(File file, String jsonObject) {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .addFormDataPart("metadata", null, RequestBody.create(MediaType.parse("application/json"), jsonObject.toString()))
                .build();
    }

    public long checkFileSize(long fileLength) {
        long fileSizeInKB = fileLength / 1024;
        return fileSizeInKB / 1024;
    }

    public String encryptedHmac(String data, String key) {
        String hmacHash = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmacHex(data);
        return hmacHash;
    }

    public File getFileByDistributionId(String distributionId) {
        String fileName = "";
        try {
            File dir = new File(approvedFilePath);
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    fileName = child.getName();
                    if (fileName.startsWith(distributionId)) {
                        return child;
                    }
                }
            }

        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return null;
    }
}