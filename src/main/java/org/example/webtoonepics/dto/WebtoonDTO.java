package org.example.webtoonepics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.entity.Webtoon;

@Data
@NoArgsConstructor
public class WebtoonDTO {

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

        @JsonProperty("outline")
        private String description;

        public Webtoon toEntity() {
            return Webtoon.builder()
                    .title(title)
                    .provider(provider)
                    .author(author)
                    .description(description)
                    .build();
        }
    }
}
