package org.example.webtoonepics.community.service;

import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.community.dto.C_commentDto;
import org.example.webtoonepics.community.dto.C_commentWriteDto;
import org.example.webtoonepics.community.dto.CommunityListDto;
import org.example.webtoonepics.community.entity.C_comment;
import org.example.webtoonepics.community.entity.Community;
import org.example.webtoonepics.public_method.exception.ResponseMessage;
import org.example.webtoonepics.community.repository.CommentRepository;
import org.example.webtoonepics.community.repository.CommunityRepository;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final CommentRepository c_commentRepository;

    public void writeComment(C_commentWriteDto writeDto, String email, Long CommunityId) {
        User user = userRepository.findByEmail(email).orElse(null);
        Community community = communityRepository.findById(CommunityId).orElse(null);
        if (community == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.NO_PAGE);
        }

        C_comment comment = C_comment.toEntity(writeDto, user, community);
        c_commentRepository.save(comment);
    }

    public Page<C_commentDto> showComment(Long communityId, PageRequest pageRequest) {
        Community community = communityRepository.findById(communityId).orElse(null);
        if (community == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.NO_PAGE);
        }
        return c_commentRepository.findWithCommunityId(communityId, pageRequest);
    }

    @Transactional
    public void updateComment(Long id, C_commentWriteDto writeDto, String email) {
        C_comment comment = c_commentRepository.findById(id).orElse(null);
        if (comment == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.NO_PAGE);
        }
        if (id != writeDto.getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.WRONG_ID);
        }
        if (!comment.getUser().getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사용자 정보가 일치하지 않습니다.");
        }
        comment.fetch(writeDto);
        c_commentRepository.save(comment);
    }

    public void deleteComment(Long id, String email) {
        C_comment comment = c_commentRepository.findById(id).orElse(null);
        if (comment == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.NO_PAGE);
        }
        if (!comment.getUser().getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사용자 정보가 일치하지 않습니다.");
        }
        c_commentRepository.delete(comment);
    }

    public Page<C_commentDto> getUserList(String email, PageRequest pageRequest) {
        Page<C_comment> byUser = c_commentRepository.findByUser(email, pageRequest);
        return byUser.map(C_commentDto::new);
    }
}
