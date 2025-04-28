package br.com.nearx.ms_objectstore.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutS3ObjectResponse {
    private UUID key;
    private String url;
    private String type;
}
