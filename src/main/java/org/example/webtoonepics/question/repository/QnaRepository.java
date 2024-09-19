package org.example.webtoonepics.question.repository;

import org.example.webtoonepics.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Question, Long> {
}
