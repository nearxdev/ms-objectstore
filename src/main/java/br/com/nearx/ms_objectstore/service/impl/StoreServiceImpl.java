package br.com.nearx.ms_objectstore.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.nearx.ms_objectstore.dto.ObjectResponse;
import br.com.nearx.ms_objectstore.dto.UploadRequest;
import br.com.nearx.ms_objectstore.exception.NotPermissionException;
import br.com.nearx.ms_objectstore.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@Slf4j
public class StoreServiceImpl implements StoreService {

    @Autowired
    private S3Client s3;

    @Override
    public ObjectResponse uploadFile(UploadRequest uploadRequest, MultipartFile file) {
        try {
            String extension = file.getOriginalFilename().split("\\.")[1];
            String contentType = "image/" + extension;
            Map<String, String> metadata = new HashMap<>();
            metadata.put("author", "Nearx");
            metadata.put("version", "1.0.0.0");
            metadata.put("content-length", String.valueOf(file.getSize()));
            metadata.put("content-type", contentType);

            InputStream inputStream = new ByteArrayInputStream(file.getBytes());

            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(uploadRequest.bucket())
                    .key(uploadRequest.folder() + "/" + uploadRequest.filename() + "." + extension)
                    .metadata(metadata)
                    .contentType(contentType)
                    .contentLength(Long.valueOf(file.getSize()))
                    .build();

            s3.putObject(putOb, RequestBody.fromInputStream(inputStream, file.getSize()));

            String url = "https://" + uploadRequest.bucket() + ".s3.us-east-1.amazonaws.com/"
                    + uploadRequest.folder() + "/" + uploadRequest.filename() + "." + extension;

            ObjectResponse objectResponse = new ObjectResponse();
            objectResponse.setUrl(url);
            objectResponse.setType(contentType);

            log.info("Successfully placed " + uploadRequest.bucket() + uploadRequest.filename() + " into bucket "
                    + uploadRequest.bucket());

            return objectResponse;

        } catch (S3Exception e) {
            log.error("Error uploading to S3: " + e.getMessage());

            if (e.getMessage().contains("Access Denied") ||
                    e.getMessage().contains("Forbidden") ||
                    e.getMessage().contains("Not authorized") ||
                    e.getMessage().contains("Permission denied")) {
                throw new NotPermissionException("Você não tem permissão para fazer upload para este bucket S3", e);
            }

            throw new RuntimeException("Falha ao fazer upload do arquivo para o S3: " + e.getMessage(), e);
        } catch (IOException e) {
            log.error("Error uploading to S3: " + e.getMessage());
            throw new RuntimeException("Falha ao fazer upload do arquivo para o S3: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteS3Object(UploadRequest objectRequest) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(objectRequest.bucket())
                    .key(objectRequest.folder() + "/" + objectRequest.filename())
                    .build();

            s3.deleteObject(deleteObjectRequest);
        } catch (S3Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete file from S3: " + e.getMessage(), e);
        }
    }
}
