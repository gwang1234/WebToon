package org.example.webtoonepics.community.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.util.UUID;

@Service
public class ImgService {

    private AmazonS3Client s3Config;

    @Autowired
    public ImgService(AmazonS3Client amazonS3Client) {

        this.s3Config = amazonS3Client;
    }

    @Value("${cloud.aws.s3.bucketName}")
    private String bucket;

    private String localLocation = "C:\\springboot_img\\";
//    private String localLocation = "/images/";



    public String imageUpload(MultipartRequest request) throws IOException, java.io.IOException {

        MultipartFile file = request.getFile("upload");

        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.indexOf("."));

        String uuidFileName = UUID.randomUUID() + ext;
        String localPath = localLocation + uuidFileName;

        File localFile = new File(localPath);
        file.transferTo(localFile);


        s3Config.putObject(new PutObjectRequest(bucket, uuidFileName, localFile).withCannedAcl(CannedAccessControlList.PublicRead));
        String s3Url = s3Config.getUrl(bucket, uuidFileName).toString();

        localFile.delete();

        return s3Url;

    }
}
