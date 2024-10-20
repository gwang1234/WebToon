"use client"; // 클라이언트 컴포넌트로 지정

import { useState } from "react";
import WebtoonsPageLick from "./components/webtoonPageLike";
import WebtoonsPageView from "./components/webtoonPageView";
import WebtoonsAll from "./components/webtoonPageAll";
import WebtoonsCategory from "./components/WebtoonsPageCategory";
import SearchBar from "./components/SearchBar";
import SearchResults from "./components/SearchResults";
import * as styles from "./styles/mainStyles";
import axios from "axios";

type Webtoon = {
  id: number;
  title: string;
  imageUrl: string;
};

export default function WebtoonsPage() {
  const [activePage, setActivePage] = useState<
    "like" | "view" | "all" | "search" | "category"
  >("all");
  const [searchResults, setSearchResults] = useState<Webtoon[]>([]);

  // 검색 요청 함수
  const handleSearch = async (searchType: string, searchKeyword: string) => {
    try {
      const response = await axios.get(
        `${process.env.NEXT_PUBLIC_API_URL}/webtoons?searchKeyword=${searchKeyword}&searchType=${searchType}&page=0`
      );

      const results = response.data.content;

      console.log(
        `${process.env.NEXT_PUBLIC_API_URL}/webtoons?searchKeyword=${searchKeyword}&searchType=${searchType}&page=0`
      );

      if (results.length === 0) {
        alert("검색 결과가 없습니다."); // 검색 결과가 없을 때 알림
      } else {
        setSearchResults(results); // 검색 결과가 있을 때만 업데이트
      }
    } catch (error) {
      console.error("검색 중 오류 발생:", error);
    }
  };

  return (
    <styles.Container>
      {/* 검색 결과가 있을 때 SearchResults 컴포넌트 표시 */}
      {searchResults.length > 0 ? (
        <SearchResults results={searchResults} />
      ) : (
        <>
          <styles.ButtonContainer>
            <styles.Button
              isActive={activePage === "all"}
              onClick={() => setActivePage("all")}
            >
              전체 웹툰
            </styles.Button>

            <styles.Button
              isActive={activePage === "like"}
              onClick={() => setActivePage("like")}
            >
              추천수 별 웹툰
            </styles.Button>

            <styles.Button
              isActive={activePage === "view"}
              onClick={() => setActivePage("view")}
            >
              조회수 별 웹툰
            </styles.Button>

            <styles.Button
              isActive={activePage === "category"}
              onClick={() => setActivePage("category")}
            >
              카테고리 별 웹툰
            </styles.Button>
          </styles.ButtonContainer>

          {/* activePage에 따라 다른 컴포넌트 표시 */}
          {activePage === "like" && <WebtoonsPageLick />}
          {activePage === "view" && <WebtoonsPageView />}
          {activePage === "all" && <WebtoonsAll />}
          {activePage === "category" && <WebtoonsCategory />}
        </>
      )}
      <SearchBar onSearch={handleSearch} /> {/* 검색 컴포넌트 추가 */}
    </styles.Container>
  );
}
