package org.example.webtoonepics.webtoon.repository;

import java.util.List;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {

    Webtoon findByTitle(String title);

    List<Webtoon> findByProvider(String provider);
}
