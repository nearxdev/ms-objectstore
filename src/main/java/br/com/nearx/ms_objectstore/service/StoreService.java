package br.com.nearx.ms_objectstore.service;

import br.com.nearx.ms_objectstore.dto.ObjectResponse;
import br.com.nearx.ms_objectstore.dto.UploadRequest;

public interface StoreService {

    ObjectResponse uploadFile(UploadRequest uploadRequest, byte[] fileContent, String contentType);

    void deleteS3Object(UploadRequest objectRequest);

}
