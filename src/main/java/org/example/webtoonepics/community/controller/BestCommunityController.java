package org.example.webtoonepics.community.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.webtoonepics.community.dto.CommunityListDto;
import org.example.webtoonepics.public_method.dto.base.DefaultRes;
import org.example.webtoonepics.public_method.exception.ResponseMessage;
import org.example.webtoonepics.public_method.exception.StatusCode;
import org.example.webtoonepics.community.service.BestCommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BestCommunityController {

    @Autowired
    BestCommunityService bestCommunityService;

    @Operation(summary = "커뮤니티 베스트", description = "조회수 100이상이면 커뮤니티 베스트가 됨")
    @GetMapping("/api/best-community")
    public ResponseEntity<?> best(@RequestParam(value = "searchKeyword", required = false, defaultValue = "")String searchKeyword,
                                  @RequestParam(value = "searchType", defaultValue = "title")String searchType,
                                  @RequestParam(value = "page", defaultValue = "0")int page) {
        try {
            PageRequest pageRequest = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "id"));
            Page<CommunityListDto> list = bestCommunityService.getBestList(searchKeyword, searchType, pageRequest);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.SERCH_WRONG + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "서버 오류: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
