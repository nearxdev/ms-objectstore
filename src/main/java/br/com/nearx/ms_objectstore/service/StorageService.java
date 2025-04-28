package br.com.nearx.ms_objectstore.service;

import br.com.nearx.ms_objectstore.dto.PutS3ObjectResponse;
import br.com.nearx.ms_objectstore.dto.UploadRequest;

public interface StorageService {

    PutS3ObjectResponse uploadFile(UploadRequest uploadRequest, byte[] fileContent, String contentType);

}
