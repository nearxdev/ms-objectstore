package br.com.nearx.ms_objectstore.dto;

public record StorageObjectRequest(String region, String bucket, String folder, String filename) {

}
