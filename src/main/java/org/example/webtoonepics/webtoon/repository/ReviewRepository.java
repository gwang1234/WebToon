package org.example.webtoonepics.webtoon.repository;

import java.util.List;
import java.util.Optional;
import org.example.webtoonepics.webtoon.entity.Review;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<List<Review>> findByWebtoonInfo(Webtoon webtoon);

    Page<Review> findByWebtoonInfo(Webtoon webtoon, Pageable pageable);
}
