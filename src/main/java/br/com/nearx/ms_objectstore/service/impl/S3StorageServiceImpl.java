package br.com.nearx.ms_objectstore.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nearx.ms_objectstore.dto.PutS3ObjectResponse;
import br.com.nearx.ms_objectstore.dto.UploadRequest;
import br.com.nearx.ms_objectstore.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@Slf4j
public class S3StorageServiceImpl implements StorageService {

    @Autowired
    private S3Client s3;

    public PutS3ObjectResponse uploadFile(UploadRequest uploadRequest, byte[] fileBytes, String contentType) {
        try {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("author", "Nearx");
            metadata.put("version", "1.0.0.0");

            metadata.put("content-length", String.valueOf(fileBytes.length));

            InputStream inputStream = new ByteArrayInputStream(fileBytes);

            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(uploadRequest.bucket())
                    .key(uploadRequest.folder() + "/" + uploadRequest.filename())
                    .metadata(metadata)
                    .contentType(contentType)
                    .contentLength(Long.valueOf(fileBytes.length))
                    .build();

            s3.putObject(putOb, RequestBody.fromInputStream(inputStream, fileBytes.length));

            String url = "https://" + uploadRequest.bucket() + ".s3.us-east-1.amazonaws.com/"
                    + uploadRequest.folder() + "/" + uploadRequest.filename();

            PutS3ObjectResponse putS3ObjectResponse = new PutS3ObjectResponse();
            putS3ObjectResponse.setKey(UUID.randomUUID());
            putS3ObjectResponse.setUrl(url);
            putS3ObjectResponse.setType(contentType);

            log.info("Successfully placed " + uploadRequest.bucket() + uploadRequest.filename() + " into bucket "
                    + uploadRequest.bucket());

            return putS3ObjectResponse;

        } catch (S3Exception e) {
            log.error("Error uploading to S3: " + e.getMessage());

            if (e.getMessage().contains("Access Denied") ||
                    e.getMessage().contains("Forbidden") ||
                    e.getMessage().contains("Not authorized") ||
                    e.getMessage().contains("Permission denied")) {
                // throw new NotPermissionException("You don't have permission to upload to this
                // S3 bucket", e);
            }

            throw new RuntimeException("Failed to upload file to S3: " + e.getMessage(), e);
        }
    }

}
