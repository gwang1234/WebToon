package org.example.webtoonepics.main.exception;

import jakarta.persistence.NoResultException;
import org.example.webtoonepics.community.dto.base.DefaultRes;
import org.example.webtoonepics.community.exception.StatusCode;
import org.example.webtoonepics.main.controller.MainController;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = MainController.class)
public class MainException {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDatabaseException(DataAccessException ex) {
        return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "DB 에러 발생"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<?> handleNoResultException(NoResultException ex) {
        return new ResponseEntity<>(DefaultRes.res(StatusCode.NOT_FOUND, "조회된 게시판이 없습니다."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
