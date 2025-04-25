# ms-objectstore
Micro serviço responsável para enviar arquivos para Bucket S3 e demais


Receber o seguinte schema no endpoint

```json
{
  "region": "us2",
  "bucket": "bucketName",
  "folder": "folderName",
  "filename": "11s11s115-d444g44g-s1d11d1"
}
```

Recursos

POST upload
  - retorno: `{"url": "https://s3...."}`
    
DELETE upload
  - retorno: 204 no_content

