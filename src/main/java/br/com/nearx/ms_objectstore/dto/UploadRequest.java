package br.com.nearx.ms_objectstore.dto;

public record UploadRequest (String region, String bucket, String folder, String filename) {
    
}
