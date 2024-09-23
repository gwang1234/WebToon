package org.example.webtoonepics.community.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webtoonepics.community.dto.CommunityDetailDto;
import org.example.webtoonepics.community.dto.CommunityListDto;
import org.example.webtoonepics.community.dto.CommunityWriteDto;
import org.example.webtoonepics.community.entity.Community;
import org.example.webtoonepics.community.service.CommunityService;
import org.example.webtoonepics.dto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/community")
public class CommunityController {

    @Autowired
    public CommunityService communityService;

    // 커뮤니티 쓰기
    @PostMapping("/write")
    public ResponseEntity<String> write(@RequestBody CommunityWriteDto writeDto, @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 사용자 정보가 없을 경우 (로그인하지 않았을 경우)
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        String email = userDetails.getUsername();

        Boolean target = communityService.writeCommu(writeDto, email);
        if (!target) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400에러");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }

    // 커뮤니티 목록
    @GetMapping("")
    public Page<CommunityListDto> communities(@RequestParam(value = "searchKeyword", required = false, defaultValue = "")String searchKeyword,
                                              @RequestParam(value = "searchType", defaultValue = "title")String searchType,
                                              @RequestParam(value = "page", defaultValue = "0")int page)
    {
        PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id"));
        return communityService.getList(searchKeyword, searchType, pageRequest);
    }

    // 커뮤니티 상세보기
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        Community community = communityService.findById(id);
        if (community == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 페이지가 존재하지 않음");
        }
        CommunityDetailDto detailDto = communityService.getCommuDetail(id, request, response);
        return ResponseEntity.status(HttpStatus.OK).body(detailDto);
    }

    // 커뮤니티 수정
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @AuthenticationPrincipal CustomUserDetails userDetails,
                                         @RequestBody CommunityWriteDto updateDto)
    {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다");
        }

        String email = communityService.getEmail(id);
        if (email == null || !Objects.equals(userDetails.getUsername(), email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("회원 정보가 다릅니다");
        }

        Community community1 = communityService.getCommunity(id);
        if (community1 == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 페이지가 존재하지 않음");
        }

        Community community = communityService.updateCommu(community1, updateDto);
        if (community == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 id 입니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }


    // 커뮤니티 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id,
                                         @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다");
        }
        String email = communityService.getEmail(id);
        if (email == null || !Objects.equals(userDetails.getUsername(), email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("회원 정보가 다릅니다");
        }

        Community community = communityService.getCommunity(id);
        if (community == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 페이지가 존재하지 않음");
        }

        communityService.deleteCommunity(community);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    // 좋아요 추가
    @PostMapping("/like-plus/{CommunityId}")
    public ResponseEntity<?> likeUP(@PathVariable Long CommunityId,
                                    @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다");
        }
        String email = userDetails.getUsername();
        Boolean alreadyLiked = communityService.likeUp(CommunityId, email);
        if (alreadyLiked) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 좋아요를 눌렀습니다");
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    // 좋아요 취소
    @PostMapping("/like-minus/{CommunityId}")
    public ResponseEntity<?> likeDown(@PathVariable Long CommunityId,
                                      @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다");
        }
        String email = userDetails.getUsername();
        Boolean i = communityService.likeDown(CommunityId, email);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

}
