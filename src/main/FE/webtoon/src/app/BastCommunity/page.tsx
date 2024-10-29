"use client"; // 클라이언트 컴포넌트로 지정

import { useEffect, useState } from "react";
import axios from "axios";
import CommunityList from "./components/CommunityList";
import Pagination from "./components/Pagination";
import LoadingMessage from "./components/LoadingMessage";
import ErrorMessage from "./components/ErrorMessage";
import * as styles from "./styles/mainStyles"; // 스타일 가져오기
import SearchBar from "./components/SearchBar"; // SearchBar 컴포넌트 추가

// Community 타입 정의
interface Community {
  id: number;
  title: string;
  content: string;
  view: number; // 조회수
  createdAt: number; // 작성일
}

export default function CommunityPage() {
  const [communities, setCommunities] = useState<Community[]>([]); // 커뮤니티 데이터를 저장
  const [searchResults, setSearchResults] = useState<Community[]>([]); // 검색 결과 저장
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [page, setPage] = useState<number>(1); // 현재 페이지
  const [totalPages, setTotalPages] = useState<number>(0); // 총 페이지 수
  const itemsPerPage = 20; // 페이지당 표시할 커뮤니티 항목 수

  // API 호출을 통해 커뮤니티 목록 가져오기
  useEffect(() => {
    const fetchCommunities = async () => {
      setLoading(true);
      try {
        const response = await axios.get(
          `${process.env.NEXT_PUBLIC_API_URL}/best-community?page=${
            page - 1
          }&size=${itemsPerPage}`
        );

        // console.log("API 응답 데이터: ", response.data);

        // 응답에서 content 배열과 totalPages 설정
        setCommunities(response.data.content);
        setTotalPages(response.data.totalPages); // 총 페이지 수 설정
      } catch (error) {
        console.error(
          "커뮤니티 데이터를 가져오는 중 오류가 발생했습니다: ",
          error
        );
        setError("데이터를 불러오는 중 오류가 발생했습니다.");
      } finally {
        setLoading(false);
      }
    };

    fetchCommunities();
  }, [page]);

  // 페이지네이션 핸들러
  const handlePageChange = (pageNumber: number) => {
    setPage(pageNumber);
  };

  // 검색 핸들러
  const handleSearch = async (searchType: string, searchKeyword: string) => {
    try {
      const response = await axios.get(
        `${process.env.NEXT_PUBLIC_API_URL}/community?searchKeyword=${searchKeyword}&searchType=${searchType}&page=0`
      );
      setSearchResults(response.data.content);
    } catch (error) {
      console.error("검색 중 오류 발생:", error);
    }
  };

  if (loading) {
    return <LoadingMessage />;
  }

  if (error) {
    return <ErrorMessage message={error} />;
  }

  return (
    <styles.Container>
      <styles.WriteButtonContainer></styles.WriteButtonContainer>
      {/* 검색 결과가 있을 경우 SearchResults 표시, 없을 경우 기본 목록 표시 */}
      {searchResults.length > 0 ? (
        // {/* 검색 결과 표시 */}
        <CommunityList communities={searchResults} />
      ) : (
        <>
          <CommunityList communities={communities} /> {/* 기본 커뮤니티 목록 */}
          <Pagination
            totalPages={totalPages}
            currentPage={page}
            onPageChange={handlePageChange}
          />
        </>
      )}
      <SearchBar onSearch={handleSearch} /> {/* 검색 컴포넌트 추가 */}
    </styles.Container>
  );
}
