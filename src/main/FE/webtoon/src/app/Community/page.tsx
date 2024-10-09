"use client"; // 클라이언트 컴포넌트로 지정

import { useEffect, useState } from "react";
import axios from "axios";
import Link from "next/link"; // Link 컴포넌트 가져오기
import CommunityList from "./components/CommunityList";
import Pagination from "./components/Pagination";
import LoadingMessage from "./components/LoadingMessage";
import ErrorMessage from "./components/ErrorMessage";
import * as styles from "./styles/mainStyles"; // 스타일 가져오기

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
          `${process.env.NEXT_PUBLIC_API_URL}/community?page=${
            page - 1
          }&size=${itemsPerPage}`
        );

        console.log("API 응답 데이터: ", response.data);

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

  if (loading) {
    return <LoadingMessage />;
  }

  if (error) {
    return <ErrorMessage message={error} />;
  }

  return (
    <styles.Container>
      <styles.WriteButtonContainer>
        <Link href="/Community/CommunityWritePage">
          <styles.WriteButton>글쓰기</styles.WriteButton>
        </Link>
      </styles.WriteButtonContainer>

      <CommunityList communities={communities} />

      <Pagination
        totalPages={totalPages}
        currentPage={page}
        onPageChange={handlePageChange}
      />
    </styles.Container>
  );
}
