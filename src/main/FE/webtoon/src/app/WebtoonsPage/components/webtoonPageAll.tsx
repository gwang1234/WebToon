"use client"; // 클라이언트 컴포넌트로 지정

import axios from "axios";
import React, { useEffect, useState } from "react";
import Image from "next/image";
import { useRouter } from "next/navigation"; // useRouter import
import * as styles from "../styles/styles"; // styled-components 스타일 임포트

// Webtoon 타입 정의
type Webtoon = {
  id: number;
  title: string;
  imageUrl: string;
};

export default function WebtoonsPage() {
  const [webtoons, setWebtoons] = useState<Webtoon[]>([]); // 웹툰 전체 목록
  const [totalPages, setTotalPages] = useState<number>(1); // 전체 페이지 수를 숫자로 설정
  const [loading, setLoading] = useState<boolean>(false); // 로딩 상태
  const [error, setError] = useState<string | null>(null); // 오류 상태
  const [page, setPage] = useState<number>(0); // 현재 페이지 상태
  const buttonsPerGroup = 10; // 한 그룹에 보여줄 페이지 번호 수
  const pageGroup = Math.floor(page / buttonsPerGroup); // 페이지 그룹 계산 수정
  const router = useRouter(); // useRouter 사용

  // API 호출을 통해 웹툰 전체 목록 가져오기
  useEffect(() => {
    const fetchWebtoons = async () => {
      setLoading(true); // 로딩 시작
      try {
        const response = await axios.get(
          `${process.env.NEXT_PUBLIC_API_URL}/webtoons?page=${page}` // 전체 웹툰 데이터 API 호출
        );
        // response.data.content가 배열이라면 그대로 상태에 설정
        if (response.data.content && Array.isArray(response.data.content)) {
          setWebtoons(response.data.content); // 전체 웹툰 데이터를 상태로 설정
        } else {
          setWebtoons([]); // 응답 데이터가 없을 경우 빈 배열 할당
        }

        // 총 페이지 수 설정
        if (response.data.totalPages) {
          setTotalPages(response.data.totalPages); // 총 페이지 수를 숫자로 설정
        }
      } catch (error) {
        console.error("웹툰 목록을 가져오는 중 오류가 발생했습니다:", error);
        setError("웹툰 목록을 불러오는 중 오류가 발생했습니다.");
      } finally {
        setLoading(false); // 로딩 상태 해제
      }
    };

    fetchWebtoons(); // API 호출
  }, [page]);

  // 현재 페이지 그룹에서 보여줄 페이지 번호 배열 생성
  const pageNumbers = Array.from(
    {
      length: Math.min(
        buttonsPerGroup,
        totalPages - pageGroup * buttonsPerGroup
      ),
    },
    (_, index) => pageGroup * buttonsPerGroup + index + 1
  );

  // 페이지 변경 핸들러
  const handlePageChange = (pageNumber: number) => {
    setPage(pageNumber - 1); // 페이지 번호에서 1을 뺀 값을 설정
  };

  // 다음 페이지 그룹으로 이동
  const handleNextGroup = () => {
    if ((pageGroup + 1) * buttonsPerGroup < totalPages) {
      setPage((pageGroup + 1) * buttonsPerGroup + 1); // 페이지 설정 수정
    }
  };

  // 이전 페이지 그룹으로 이동
  const handlePrevGroup = () => {
    if (pageGroup > 0) {
      setPage((pageGroup - 1) * buttonsPerGroup + 1); // 페이지 설정 수정
    }
  };

  if (error) {
    return <styles.ErrorMessage>{error}</styles.ErrorMessage>; // 오류가 있을 때 출력
  }

  // 클릭한 웹툰의 id로 이동
  const handleWebtoonClick = (id: number) => {
    router.push(`/WebtoonsPage/${id}`); // router를 사용하여 경로 이동
  };

  return (
    <div>
      {/* 웹툰 목록 섹션 */}
      <styles.Section>
        <styles.WebtoonGrid>
          {loading ? (
            <styles.LoadingMessage>로딩 중...</styles.LoadingMessage> // 로딩 중일 때 메시지
          ) : webtoons.length > 0 ? (
            webtoons.map((webtoon) => (
              <styles.WebtoonCard
                key={webtoon.title}
                onClick={() => handleWebtoonClick(webtoon.id)}
              >
                {/* 웹툰 프로필 이미지 */}
                <Image
                  src={webtoon.imageUrl || "/default-image.jpg"} // 기본 이미지 설정
                  alt={webtoon.title}
                  width={100}
                  height={150}
                  priority
                  unoptimized
                />
                <p>{webtoon.title}</p>
              </styles.WebtoonCard>
            ))
          ) : (
            <p>웹툰이 없습니다.</p> // 데이터가 없는 경우
          )}
        </styles.WebtoonGrid>

        {/* 페이지네이션 */}
        <styles.PaginationContainer>
          <styles.PageButton
            disabled={pageGroup === 0}
            onClick={handlePrevGroup}
          >
            이전
          </styles.PageButton>

          {pageNumbers.map((pageNumber) => (
            <styles.PageButton
              key={pageNumber}
              isActive={page === pageNumber - 1} // 현재 페이지와 비교
              onClick={() => handlePageChange(pageNumber)}
            >
              {pageNumber}
            </styles.PageButton>
          ))}

          <styles.PageButton
            disabled={(pageGroup + 1) * buttonsPerGroup >= totalPages}
            onClick={handleNextGroup}
          >
            다음
          </styles.PageButton>
        </styles.PaginationContainer>
      </styles.Section>
    </div>
  );
}
