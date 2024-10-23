"use client";

import { useState, useEffect } from "react";
import { useRouter } from "next/navigation"; // useRouter import
import Image from "next/image"; // Next.js Image 컴포넌트 import
import * as styles from "../styles/styles"; // styled-components 스타일 임포트

type Webtoon = {
  id: number;
  title: string;
  imageUrl: string; // imageUrl -> imageurl로 변경
};

type SearchResultsProps = {
  results: Webtoon[];
};

export default function SearchResults({ results }: SearchResultsProps) {
  const [page, setPage] = useState<number>(1); // 현재 페이지 상태
  const [pageGroup, setPageGroup] = useState<number>(0); // 페이지 그룹 상태
  const [loading, setLoading] = useState<boolean>(false); // 로딩 상태 추가
  const itemsPerPage = 20; // 페이지당 표시할 웹툰 수
  const buttonsPerGroup = 10; // 한 그룹에 보여줄 페이지 번호 수
  const totalPages = Math.ceil(results.length / itemsPerPage); // 총 페이지 수 계산
  const router = useRouter(); // useRouter 사용

  // 데이터 로딩 중 처리
  useEffect(() => {
    if (results.length === 0) {
      setLoading(true); // 로딩 시작
    } else {
      setLoading(false); // 로딩 완료
    }
  }, [results]);

  // 현재 페이지의 웹툰 목록을 계산
  const currentWebtoons = results.slice(
    (page - 1) * itemsPerPage,
    page * itemsPerPage
  );

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
    setPage(pageNumber); // 페이지 번호 변경
  };

  // 다음 페이지 그룹으로 이동
  const handleNextGroup = () => {
    if ((pageGroup + 1) * buttonsPerGroup < totalPages) {
      setPageGroup(pageGroup + 1);
    }
  };

  // 이전 페이지 그룹으로 이동
  const handlePrevGroup = () => {
    if (pageGroup > 0) {
      setPageGroup(pageGroup - 1);
    }
  };

  // 클릭한 웹툰의 id로 이동
  const handleWebtoonClick = (id: number) => {
    router.push(`/WebtoonsPage/${id}`); // router를 사용하여 경로 이동
  };

  return (
    <div>
      {/* 웹툰 목록 */}
      <styles.Section>
        <styles.WebtoonGrid>
          {loading ? (
            <styles.LoadingMessage>로딩 중...</styles.LoadingMessage> // 로딩 중일 때 메시지
          ) : currentWebtoons.length > 0 ? (
            currentWebtoons.map((webtoon) => (
              <styles.WebtoonCard
                key={webtoon.id}
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
              isActive={page === pageNumber}
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
