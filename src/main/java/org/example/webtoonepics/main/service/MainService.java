package org.example.webtoonepics.main.service;

import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.main.dto.MainWebtoonDto;
import org.example.webtoonepics.webtoon.repository.LikewebtoonRepository;
import org.example.webtoonepics.webtoon.repository.WebtoonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final WebtoonRepository webtoonRepository;
    private final LikewebtoonRepository likewebtoonRepository;

    Pageable topTen = PageRequest.of(0, 10);

    public List<MainWebtoonDto> findLikeTop10() {
        return likewebtoonRepository.findTop10(topTen);
    }

    public List<MainWebtoonDto> findViewTop10() {
        return webtoonRepository.findTop10Views(topTen);
    }

    public Page<MainWebtoonDto> AllLikes(int page) {
        PageRequest pageRequest = PageRequest.of(page, 25);
        return likewebtoonRepository.findTop(pageRequest);
    }

    public Page<MainWebtoonDto> AllViews(int page) {
        PageRequest pageRequest = PageRequest.of(page, 25);
        return webtoonRepository.findViews(pageRequest);
    }
}
