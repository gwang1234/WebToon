package org.example.webtoonepics.community.repository;

import org.example.webtoonepics.community.dto.CommunityDetailDto;

public interface CommunityCustom {
    CommunityDetailDto findCommuDetail(Long id);
}
