package br.com.nearx.ms_objectstore.service;

import org.springframework.web.multipart.MultipartFile;

import br.com.nearx.ms_objectstore.dto.ObjectResponse;
import br.com.nearx.ms_objectstore.dto.UploadRequest;

public interface StoreService {

    ObjectResponse uploadFile(UploadRequest uploadRequest, MultipartFile file);

    void deleteS3Object(UploadRequest objectRequest);

}
