package org.example.webtoonepics.community.service;

import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.community.dto.CommunityListDto;
import org.example.webtoonepics.community.entity.Community;
import org.example.webtoonepics.community.repository.CommentRepository;
import org.example.webtoonepics.community.repository.CommunityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BestCommunityService {

    private final CommunityRepository communityRepository;

    public Page<CommunityListDto> getBestList(String searchKeyword, String searchType, PageRequest pageRequest) {
        Page<Community> communities = null;
        if (searchType.equals("title")) {
            communities = communityRepository.findByTitleAndView100(searchKeyword, pageRequest);
        } else if (searchType.equals("content")) {
            communities = communityRepository.findByContentAndView100(searchKeyword, pageRequest);
        } else if (searchType.equals("titleContent")) {
            communities = communityRepository.findByTitleOrContentAndView100(searchKeyword, pageRequest);
        }
        return communities == null ? Page.empty() : communities.map(CommunityListDto::new);
    }
}
