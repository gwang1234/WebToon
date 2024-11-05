package org.example.webtoonepics.community.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webtoonepics.test.Test;
import org.example.webtoonepics.test.TestDto;
import org.example.webtoonepics.test.TestRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AwsFileService implements FileService{

    private final TestRepository testRepository;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucket;

    private String MEMBER_IMG_DIR = "community/";


    // 이미지 URL 가져오기
    @Override
    public String getFileUrl(String fileName) {
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

        @Override
        public String savePhoto(MultipartFile multipartFile, Long commuId) throws IOException, java.io.IOException {
            return uploadToS3(multipartFile, MEMBER_IMG_DIR, commuId);
        }

        // S3로 파일 업로드하기
        private String uploadToS3(MultipartFile file, String dirName, Long memberId) throws IOException, java.io.IOException {
            String fileName = dirName + memberId + "/" + UUID.randomUUID() + file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            }

            return amazonS3Client.getUrl(bucket, fileName).toString();
        }
}
