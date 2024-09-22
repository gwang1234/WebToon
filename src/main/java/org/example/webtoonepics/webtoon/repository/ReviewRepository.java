package org.example.webtoonepics.webtoon.repository;

import java.util.List;
import java.util.Optional;
import org.example.webtoonepics.webtoon.entity.Review;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<List<Review>> findByWebtoonInfo(Webtoon webtoon);
}
