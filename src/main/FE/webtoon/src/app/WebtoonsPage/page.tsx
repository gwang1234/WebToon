"use client"; // 클라이언트 컴포넌트로 지정

import { useState } from "react";
import WebtoonsPageLick from "./components/webtoonPageLike";
import WebtoonsPageView from "./components/webtoonPageView";
import WebtoonsAll from "./components/webtoonPageAll"; // 새로운 WebtoonsAll 컴포넌트 추가
import SearchBar from "./components/SearchBar"; // SearchBar 컴포넌트 추가
import SearchResults from "./components/SearchResults"; // SearchResults 컴포넌트 추가
import * as styles from "./styles/mainStyles";
import axios from "axios";

type Webtoon = {
  id: number;
  title: string;
  imageUrl: string;
};

export default function WebtoonsPage() {
  const [activePage, setActivePage] = useState<
    "like" | "view" | "all" | "search"
  >("like");
  const [searchResults, setSearchResults] = useState<Webtoon[]>([]);

  // 검색 요청 함수
  const handleSearch = async (searchType: string, searchKeyword: string) => {
    try {
      const response = await axios.get(
        `${process.env.NEXT_PUBLIC_API_URL}/webtoons?searchKeyword=${searchKeyword}&searchType=${searchType}&page=0`
      );
      setSearchResults(response.data.content);
    } catch (error) {
      console.error("검색 중 오류 발생:", error);
    }
  };

  return (
    <styles.Container>
      <SearchBar onSearch={handleSearch} /> {/* 검색 컴포넌트 추가 */}
      {/* 검색 결과가 있을 때 SearchResults 컴포넌트 표시 */}
      {searchResults.length > 0 ? (
        <SearchResults results={searchResults} />
      ) : (
        <>
          <styles.ButtonContainer>
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
              좋아요 별 웹툰
            </styles.Button>
            <styles.Button
              isActive={activePage === "all"}
              onClick={() => setActivePage("all")}
            >
              전체 웹툰
            </styles.Button>
          </styles.ButtonContainer>

          {/* activePage에 따라 다른 컴포넌트 표시 */}
          {activePage === "like" && <WebtoonsPageLick />}
          {activePage === "view" && <WebtoonsPageView />}
          {activePage === "all" && <WebtoonsAll />}
        </>
      )}
    </styles.Container>
  );
}
