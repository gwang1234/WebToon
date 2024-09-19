package org.example.webtoonepics.repository;

import org.example.webtoonepics.entity.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {

    Webtoon findByTitle(String title);
}
