package org.example.webtoonepics.webtoon.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.example.webtoonepics.webtoon.dto.ReviewRequest;
import org.example.webtoonepics.webtoon.entity.Review;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.example.webtoonepics.webtoon.service.ReviewService;
import org.example.webtoonepics.webtoon.service.WebtoonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private WebtoonService webtoonService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void mocMvcSetup() {
        // MockMvc 설정
    }

    @Test
    @DisplayName("리뷰 생성 성공 테스트")
    public void writeReview() throws Exception {
        // given
        ReviewRequest reviewRequest = new ReviewRequest(2L, "나다", "내가 슬프게 해줄께", (short) 4);
        Webtoon webtoon = webtoonService.findById(reviewRequest.getWebtoonId());

        Review savedReview = reviewRequest.toEntity(webtoon);

        when(reviewService.save(any(ReviewRequest.class))).thenReturn(savedReview);

        String requestBody = objectMapper.writeValueAsString(reviewRequest);

        // when
        ResultActions result = mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated())
                .andDo(print());

        String jsonResponse = new String(result.andReturn().getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        Review createdReview = objectMapper.readValue(jsonResponse, Review.class);

        assertThat(createdReview.getWriter()).isEqualTo(savedReview.getWriter());
        assertThat(createdReview.getContent()).isEqualTo(savedReview.getContent());
        assertThat(createdReview.getStar()).isEqualTo(savedReview.getStar());
    }
}
