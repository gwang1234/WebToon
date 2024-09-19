package org.example.webtoonepics.community.controller;

import org.example.webtoonepics.community.dto.CommunityDto;
import org.example.webtoonepics.community.dto.CommunityWriteDto;
import org.example.webtoonepics.community.service.CommunityService;
import org.example.webtoonepics.dto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    public CommunityService communityService;

    // 커뮤니티 쓰기
    @PostMapping("/write")
    public ResponseEntity<String> write(@RequestBody CommunityWriteDto writeDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 사용자 정보가 없을 경우 (로그인하지 않았을 경우)
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        String email = userDetails.getUsername();

        Boolean target = communityService.writeCommu(writeDto, email);
        if (!target) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400에러");
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    // 커뮤니티 목록
    @GetMapping("")
    public Page<CommunityDto> communities(
            @RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
            @RequestParam(value = "searchType", defaultValue = "title") String searchType,
            @RequestParam(value = "page", defaultValue = "0") int page) {
        PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id"));
        return communityService.getList(searchKeyword, searchType, pageRequest);
    }

    // 커뮤니티 상세보기
//    @GetMapping("/detail/{id}")
//    public ResponseEntity<CommunityDetailDto> detail(@PathVariable Long id) {
//        CommunityDetailDto detailDto = communityService.getCommuDetail(id);
//        return ResponseEntity.status(HttpStatus.OK).body(detailDto);
//    }

    // 커뮤니티 수정

    // 커뮤니티 삭제
}
