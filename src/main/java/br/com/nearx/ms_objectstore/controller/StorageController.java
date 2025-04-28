package br.com.nearx.ms_objectstore.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.nearx.ms_objectstore.dto.ObjectResponse;
import br.com.nearx.ms_objectstore.dto.UploadRequest;
import br.com.nearx.ms_objectstore.service.StorageService;

@RestController
@RequestMapping("storage")
public class StorageController {
    @Autowired
    private StorageService storageService;

    @PostMapping
    public ResponseEntity<ObjectResponse> uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestPart("request") UploadRequest request) {

        try {
            byte[] fileBytes = file.getBytes();
            String contentType = file.getContentType();

            ObjectResponse response = storageService.uploadFile(request, fileBytes, contentType);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
}
