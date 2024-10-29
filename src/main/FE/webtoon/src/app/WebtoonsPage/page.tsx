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
  const [totalPages, setTotalPages] = useState<number>(1); // 총 페이지 수 상태
  const [page, setPage] = useState<number>(0); // 현재 페이지 상태
  const [searchKeyword, setSearchKeyword] = useState<string>(""); // 검색어 상태
  const [searchType, setSearchType] = useState<string>(""); // 검색 타입 상태

  // 검색 요청 함수
  const handleSearch = async (searchType: string, searchKeyword: string) => {
    setSearchKeyword(searchKeyword);
    setSearchType(searchType);
    setPage(0); // 새로운 검색 시 페이지 번호를 0으로 초기화
    fetchWebtoons(0, searchType, searchKeyword); // 첫 페이지로 요청
  };

  // 웹툰 데이터 가져오기 함수
  const fetchWebtoons = async (
    page: number,
    searchType: string,
    searchKeyword: string
  ) => {
    try {
      const response = await axios.get(
        `${process.env.NEXT_PUBLIC_API_URL}/webtoons?searchKeyword=${searchKeyword}&searchType=${searchType}&page=${page}`
      );
      const results = response.data.content;

      if (results.length === 0) {
        alert("검색 결과가 없습니다.");
      } else {
        setSearchResults(results);
        setActivePage("search"); // 검색 결과 페이지로 전환
      }

      // 총 페이지 수 설정
      if (response.data.totalPages) {
        setTotalPages(response.data.totalPages); // 총 페이지 수를 상태로 설정
      }
    } catch (error) {
      console.error("검색 중 오류 발생:", error);
    }
  };

  // 페이지 변경 핸들러
  const handlePageChange = (newPage: number) => {
    setPage(newPage); // 페이지 상태 업데이트
    fetchWebtoons(newPage, searchType, searchKeyword); // 페이지 변경 시 다시 요청
  };

  return (
    <styles.Container>
      {/* 검색 결과가 있을 때 SearchResults 컴포넌트로 현재 페이지와 총 페이지 수 전달 */}
      {searchResults.length > 0 ? (
        <SearchResults
          results={searchResults}
          totalPages={totalPages}
          currentPage={page} // 현재 페이지 상태 전달
          onPageChange={handlePageChange} // 페이지 변경 핸들러 전달
        />
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
