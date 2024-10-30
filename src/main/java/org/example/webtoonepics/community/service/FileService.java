package org.example.webtoonepics.community.service;

import io.jsonwebtoken.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String savePhoto(MultipartFile multipartFile, Long memberId) throws IOException, java.io.IOException;

    String getFileUrl(String filename);

}
