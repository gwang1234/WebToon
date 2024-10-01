package org.example.webtoonepics.webtoon.service;

import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.user.repository.UserRepository;
import org.example.webtoonepics.webtoon.entity.Likewebtoon;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.example.webtoonepics.webtoon.repository.LikewebtoonRepository;
import org.example.webtoonepics.webtoon.repository.WebtoonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikewebtoonService {

    private final LikewebtoonRepository likewebtoonRepository;
    private final WebtoonRepository webtoonRepository;
    private final UserRepository userRepository;

    private final int ADD_LIKE = 1;
    private final int DELETE_LIKE = -1;

    public Long getLikes(Long webtoonId, Long userId) {
        Webtoon webtoon = webtoonRepository.findById(webtoonId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid webtoon ID"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        return likewebtoonRepository.countByWebtoonInfoAndUserInfo(webtoon, user);
    }

    @Transactional
    public int likeWebtoon(Long webtoonId, Long userId) {
        Webtoon webtoon = webtoonRepository.findById(webtoonId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid webtoon ID"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Likewebtoon likewebtoon = new Likewebtoon(webtoon, user);

        // 좋아요 정보 조회
        Likewebtoon findLikewebtoon = likewebtoonRepository.findByWebtoonInfoAndUserInfo(webtoon, user).orElse(null);

        // 이미 유저가 좋아요를 누른 상태이면 삭제
        if (findLikewebtoon == null) {
            likewebtoonRepository.save(likewebtoon);
            return ADD_LIKE;
        } else {
            likewebtoonRepository.delete(findLikewebtoon);
            return DELETE_LIKE;
        }
    }
}