package org.example.webtoonepics.webtoon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.webtoon.entity.Webtoon;

@Data
@NoArgsConstructor
public class WebtoonRequest {

    @JsonProperty("result")
    private Result result;

    @JsonProperty("itemList")
    private List<ItemList> itemList;  // 변수명을 JSON 응답에 맞게 수정

    @Data
    public static class Result {

        @JsonProperty("pageNo")
        private int pageNo;

        @JsonProperty("resultState")
        private String resultState;

        @JsonProperty("totalCount")
        private int totalCount;

        @JsonProperty("viewItemCnt")
        private int viewItemCnt;

        @JsonProperty("resultMessage")
        private String resultMessage;
    }

    @Data
    public static class ItemList {

        @JsonProperty("title")
        private String title;

        @JsonProperty("pltfomCdNm")
        private String provider;

        @JsonProperty("sntncWritrNm")
        private String author;

        @JsonProperty("mainGenreCdNm")
        private String genre;

        @JsonProperty("outline")
        private String description;

        public Webtoon toEntity() {
            return Webtoon.builder()
                    .title(title)
                    .provider(provider)
                    .author(author)
                    .genre(genre)
                    .description(description)
                    .build();
        }
    }
}
