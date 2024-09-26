package org.example.webtoonepics.question.repository;

import org.example.webtoonepics.question.dto.QuestionListDto;
import org.example.webtoonepics.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QnaRepository extends JpaRepository<Question, Long> {
    @Query("select new org.example.webtoonepics.question.dto.QuestionListDto(q.id, q.title, u.userName, q.created_at)" +
            "from Question q inner join q.user u")
    Page<QuestionListDto> findQnaList(Pageable pageable);
}
