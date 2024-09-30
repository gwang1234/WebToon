package org.example.webtoonepics.community.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webtoonepics.community.dto.CommunityDetailDto;
import org.example.webtoonepics.community.dto.CommunityListDto;
import org.example.webtoonepics.community.dto.CommunityWriteDto;
import org.example.webtoonepics.community.entity.C_comment;
import org.example.webtoonepics.community.entity.Community;
import org.example.webtoonepics.community.entity.Like_community;
import org.example.webtoonepics.community.repository.CommentRepository;
import org.example.webtoonepics.community.repository.CommunityRepository;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.user.repository.UserRepository;
import org.example.webtoonepics.community.repository.LikeCommunityRepository;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommunityService {

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final CommentRepository commentRepository;
    private final LikeCommunityRepository likeCommunityRepository;

    public Page<CommunityListDto> getList(String searchKeyword, String searchType, PageRequest pageRequest) {
        Page<Community> communities = null;
        if (searchType.equals("title")) {
            communities = communityRepository.findByTitleContaining(searchKeyword, pageRequest);
        } else if (searchType.equals("content")) {
            communities = communityRepository.findByContentContaining(searchKeyword, pageRequest);
        } else if (searchType.equals("titleContent")) {
            communities = communityRepository.findByTitleOrContentContaining(searchKeyword, pageRequest);
        }
        return communities == null ? Page.empty() : communities.map(CommunityListDto::new);
    }

    public Boolean writeCommu(CommunityWriteDto writeDto, String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        Community community = Community.toEntity(writeDto, user);
        Community save = communityRepository.save(community);

        if (!Hibernate.isInitialized(save)) {
            return false;
        }
        return true;
    }

    @Transactional
    public CommunityDetailDto getCommuDetail(Long id, HttpServletRequest request, HttpServletResponse response) {
        CommunityDetailDto commuDetail = communityRepository.findCommuDetail(id);
        if (commuDetail == null) {
            return null;
        }
        if(!isArticleIdInCookies(request, id)) {
            commuDetail.setView(commuDetail.getView()+1);
            communityRepository.viewUpdate(id);
            addArticleIdToCookies(response, id);
        }
        return commuDetail;
    }

    // 쿠키에 게시물 ID 추가 (중복 조회 방지)
    private void addArticleIdToCookies(HttpServletResponse response, Long articleId) {
        Cookie cookie = new Cookie("viewed_article_" + articleId, "true");
        cookie.setMaxAge(24 * 60 * 60); // 1일 유지
        response.addCookie(cookie);
    }


    // 쿠키에서 해당 게시물 ID가 있는지 확인
    private boolean isArticleIdInCookies(HttpServletRequest request, Long articleId) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (("viewed_article_" + articleId).equals(cookie.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public Community getCommunity(Long id) {
        return communityRepository.findById(id).orElse(null);
    }

    @Transactional
    public Community updateCommu(Community community, CommunityWriteDto updateDto) {
        if (community.getId() != updateDto.getId()) {
            System.out.println(community.getId());
            System.out.println(updateDto.getId());
            return null;
        }
        community.fetch(updateDto);

        return communityRepository.save(community);
    }

    public String getEmail(Long id) {
        String email = communityRepository.findEmail(id).orElse(null);
        log.info(email);
        return email;
    }

    @Transactional
    public void like(Long id, String email) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 존재하지 않습니다"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));

        Like_community likeCommunity = likeCommunityRepository.findByUserAndCommunity(user, community);

        if (likeCommunity != null){
            likeCommunity.setLikes(likeCommunity.getLikes() == 1 ? 0 : 1);
            likeCommunityRepository.save(likeCommunity);
        } else {
            Like_community entity = Like_community.toEntity(community, user);
            likeCommunityRepository.save(entity);
        }

    }

    public int getlikes(Long communityId) {
        return likeCommunityRepository.likeCount(communityId);
    }


    public void deleteCommunity(Community community) {
        List<Like_community> byCommunity = likeCommunityRepository.findByCommunity(community);
        likeCommunityRepository.deleteAll(byCommunity);
        List<C_comment> comments = commentRepository.findByCommunity(community);
        commentRepository.deleteAll(comments);
        communityRepository.delete(community);
    }

    public Community findById(Long id) {
        return communityRepository.findById(id).orElse(null);
    }

}

