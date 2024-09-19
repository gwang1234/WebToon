package org.example.webtoonepics.community.service;

import org.example.webtoonepics.community.dto.CommunityDetailDto;
import org.example.webtoonepics.community.dto.CommunityDto;
import org.example.webtoonepics.community.dto.CommunityWriteDto;
import org.example.webtoonepics.community.entity.Community;
import org.example.webtoonepics.community.repository.CommunityRepository;
import org.example.webtoonepics.entity.User;
import org.example.webtoonepics.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

@Service
public class CommunityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommunityRepository communityRepository;

    public Page<CommunityDto> getList(String searchKeyword, String searchType, PageRequest pageRequest) {
        Page<Community> communities = null;
        if (searchType.equals("title")){
            communities = communityRepository.findByTitleContaining(searchKeyword, pageRequest);
        } else if (searchType.equals("content")){
            communities = communityRepository.findByContentContaining(searchKeyword, pageRequest);
        } else if (searchType.equals("titleContent")) {
            communities = communityRepository.findByTitleOrContentContaining(searchKeyword, pageRequest);
        }
        return communities == null ? Page.empty() : communities.map(CommunityDto::new);
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

    public CommunityDetailDto getCommuDetail(Long id) {
        return communityRepository.findCommuDetail(id);
    }

}

