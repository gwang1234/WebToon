package org.example.webtoonepics.community.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AwsFileServiceTest {

    @Autowired
    AwsFileService awsFileService;

    @Test
    public void ImgSave() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "img.png",
                "image/png",
                new FileInputStream("src\\test\\resources\\images\\img.png") // 테스트용 이미지 파일 경로
        );

        String result = awsFileService.savePhoto(multipartFile, 1L);

        assertNotNull(result); // 결과 URL이 null이 아닌지 확인
    }
}