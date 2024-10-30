package org.example.webtoonepics.community.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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

    @Test
    public void ShowImg() {
        String fileUrl = awsFileService.getFileUrl("community/1/dac8a9d1-1df6-45d6-b9c4-8080a289b43fimg.png");

        // URL이 유효한지 검사
        assertNotNull(fileUrl);
        System.out.println("S3 이미지 URL: " + fileUrl);

        // URL을 통해 이미지 불러오기가 가능한지 확인
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            // 응답이 200인지 확인
            assertEquals(200, responseCode);
        } catch (IOException e) {
            fail("이미지 URL 확인 실패: " + e.getMessage());
        }
    }

}