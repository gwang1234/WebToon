package org.example.webtoonepics.question.service;

import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.question.dto.QuestionDto;
import org.example.webtoonepics.question.dto.QuestionListDto;
import org.example.webtoonepics.question.entity.Question;
import org.example.webtoonepics.question.repository.QnaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.example.webtoonepics.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final UserRepository userRepository;
    private final QnaRepository qnaRepository;
    public void writeQnA(String email, QuestionDto questionDto) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사용자 정보가 없습니다.");
        }
        Question question = new Question(questionDto.getId(),
                questionDto.getTitle(), questionDto.getContent(), user, LocalDateTime.now(), LocalDateTime.now());
        qnaRepository.save(question);
    }

    public Page<QuestionListDto> getQnaList(PageRequest pageRequest) {
        Page<QuestionListDto> qnaList = qnaRepository.findQnaList(pageRequest);
        return qnaList == null ? Page.empty() : qnaList;
    }
}
