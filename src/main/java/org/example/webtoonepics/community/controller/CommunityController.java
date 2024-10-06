package org.example.webtoonepics.community.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webtoonepics.community.dto.CommunityDetailDto;
import org.example.webtoonepics.community.dto.CommunityListDto;
import org.example.webtoonepics.community.dto.CommunityProvideDto;
import org.example.webtoonepics.community.dto.CommunityWriteDto;
import org.example.webtoonepics.community.dto.base.DefaultRes;
import org.example.webtoonepics.community.entity.Community;
import org.example.webtoonepics.community.exception.ResponseMessage;
import org.example.webtoonepics.community.exception.StatusCode;
import org.example.webtoonepics.community.service.CommunityService;
import org.example.webtoonepics.jwt_login.dto.CustomUserDetails;
import org.example.webtoonepics.user.controller.OAuth2Controllor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/community")
public class CommunityController {

    @Autowired
    OAuth2Controllor oAuth2Controllor;

    @Autowired
    public CommunityService communityService;

    // 커뮤니티 쓰기
    @Operation(summary = "커뮤니티 글쓰기", description = "header로 로그인 정보를 받아와서 게시판 작성")
    @PostMapping("/write")
    public ResponseEntity<DefaultRes<String>> write(@RequestBody CommunityWriteDto writeDto,
                                                    @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        if (userDetails == null && (writeDto.getProvider_id() == null || writeDto.getProvider_id().isEmpty())) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
        }
        if (userDetails != null && (writeDto.getProvider_id() != null && !writeDto.getProvider_id().isEmpty())) {
            SecurityContextHolder.clearContext();
            userDetails = null;
        }
        try {
            // 사용자 이메일 가져오기
            String email;
            if (userDetails != null) {
                email = userDetails.getUsername();
            } else {
                email = communityService.findByProviderId(writeDto.getProvider_id());
            }

            Boolean target = communityService.writeCommu(writeDto, email);
            if (!target) {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NO_WRITE), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(DefaultRes.res(StatusCode.CREATED, ResponseMessage.SUCCESS), HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "에러 발생: " + errorMessage));
        }
    }

    // 커뮤니티 목록
    @Operation(summary = "커뮤니티 목록", description = "?searchKeyword=&searchType=title&page=0 검색어, 콤보박스(글, 내용, 글+내용), 페이지 중심으로 게뮤니티 목록 가져오기")
    @GetMapping("")
    public ResponseEntity<?> communities(@RequestParam(value = "searchKeyword", required = false, defaultValue = "")String searchKeyword,
                                              @RequestParam(value = "searchType", defaultValue = "title")String searchType,
                                              @RequestParam(value = "page", defaultValue = "0")int page)
    {
        try {
            PageRequest pageRequest = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "id"));
            Page<CommunityListDto> list = communityService.getList(searchKeyword, searchType, pageRequest);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.SERCH_WRONG + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "서버 오류: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // 커뮤니티 상세보기
    @Operation(summary = "커뮤니티 글 상세보기", description = "쿠키값 설정 조회수 중복 방지(10분)")
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
    {
        try {
            Community community = communityService.findById(id);
            if (community == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NO_PAGE));
            }
            CommunityDetailDto detailDto = communityService.getCommuDetail(id, request, response);
            return new ResponseEntity<>(detailDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.SERCH_WRONG + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "서버 오류: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // 커뮤니티 수정
    @Operation(summary = "커뮤니티 수정", description = "header로 로그인 정보를 받아와서 해당 유저 게시판 수정")
    @PatchMapping("/update/{id}")
    public ResponseEntity<DefaultRes<String>> update(@PathVariable Long id,
                                                     @AuthenticationPrincipal CustomUserDetails userDetails,
                                         @RequestBody CommunityWriteDto updateDto)
    {
        if (userDetails == null && (updateDto.getProvider_id() == null || updateDto.getProvider_id().isEmpty())) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
        }
        if (userDetails != null && (updateDto.getProvider_id() != null && !updateDto.getProvider_id().isEmpty())) {
            SecurityContextHolder.clearContext();
            userDetails = null;
        }

        try {
            // 사용자 이메일 가져오기
            String email;
            if (userDetails != null) {
                email = userDetails.getUsername();
            } else {
                email = communityService.findByProviderId(updateDto.getProvider_id());
            }

            String email1 = communityService.getEmail(id);
            if (email == null || !Objects.equals(email1, email)) {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.FORBIDDEN, ResponseMessage.DIFF_USER), HttpStatus.FORBIDDEN);
            }

            Community community1 = communityService.getCommunity(id);
            if (community1 == null) {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NO_PAGE), HttpStatus.BAD_REQUEST);
            }

            Community community = communityService.updateCommu(community1, updateDto);
            if (community == null) {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.WRONG_ID), HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.SERCH_WRONG + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "서버 오류: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // 커뮤니티 삭제
    @Operation(summary = "커뮤니티 삭제", description = "header로 로그인 정보를 받아와서 해당 유저 게시판 삭제")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DefaultRes<String>> delete(@PathVariable Long id,
                                                     @RequestBody(required = false) CommunityProvideDto deleteDto,
                                                     @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        if (userDetails == null && (deleteDto.getProvider_id() == null || deleteDto.getProvider_id().isEmpty())) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
        }
        if (userDetails != null && (deleteDto.getProvider_id() != null && !deleteDto.getProvider_id().isEmpty())) {
            SecurityContextHolder.clearContext();
            userDetails = null;
        }

        try {
            // 사용자 이메일 가져오기
            String useremail;
            if (userDetails != null) {
                useremail = userDetails.getUsername();
            } else {
                useremail = communityService.findByProviderId(deleteDto.getProvider_id());
            }

            Community community = communityService.getCommunity(id);
            if (community == null) {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NO_PAGE), HttpStatus.BAD_REQUEST);
            }
            String email = communityService.getEmail(id);
            if (email == null || !Objects.equals(useremail, email)) {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.FORBIDDEN, ResponseMessage.DIFF_USER), HttpStatus.FORBIDDEN);
            }
            communityService.deleteCommunity(community);
            return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.SERCH_WRONG + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "서버 오류: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 좋아요
    @Operation(summary = "게시판 좋아요 설정", description = "header로 로그인 정보를 받아와서 해당 게시판 좋아요 추가 or 삭제")
    @PostMapping("/like-set/{CommunityId}")
    public ResponseEntity<DefaultRes<String>> likeSet(@PathVariable Long CommunityId,
                                                      @RequestBody(required = false) CommunityProvideDto communityWriteDto,
                                                      @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        if (userDetails == null && (communityWriteDto.getProvider_id() == null || communityWriteDto.getProvider_id().isEmpty())) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
        }
        if (userDetails != null && (communityWriteDto.getProvider_id() != null && !communityWriteDto.getProvider_id().isEmpty())) {
            SecurityContextHolder.clearContext();
            userDetails = null;
        }

        try {
            // 사용자 이메일 가져오기
            String useremail;
            if (userDetails != null) {
                useremail = userDetails.getUsername();
            } else {
                useremail = communityService.findByProviderId(communityWriteDto.getProvider_id());
            }

            communityService.like(CommunityId, useremail);
            return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.SERCH_WRONG + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "서버 오류: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "게시판 좋아요 조회", description = "해당 게시판 좋아요 수 조회")
    @GetMapping("/like-get/{CommunityId}")
    public ResponseEntity<?> likeGet(@PathVariable Long CommunityId) {
        try {
            int getlikes = communityService.getlikes(CommunityId);
            return ResponseEntity.status(HttpStatus.OK).body(getlikes);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.SERCH_WRONG + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "서버 오류: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
