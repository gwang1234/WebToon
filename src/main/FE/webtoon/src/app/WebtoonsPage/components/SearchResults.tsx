"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation"; // useRouter import
import Image from "next/image"; // Next.js Image 컴포넌트 import
import * as styles from "../styles/styles"; // styled-components 스타일 임포트

type Webtoon = {
  id: number;
  title: string;
  imageUrl: string;
};

type SearchResultsProps = {
  results: Webtoon[];
  totalPages: number; // 총 페이지 수 전달받음
  currentPage: number; // 현재 페이지 전달받음
  onPageChange: (pageNumber: number) => void; // 페이지 변경 핸들러
};

export default function SearchResults({
  results,
  totalPages,
  currentPage,
  onPageChange,
}: SearchResultsProps) {
  const [pageGroup, setPageGroup] = useState<number>(0); // 페이지 그룹 상태
  const [loading, setLoading] = useState<boolean>(false); // 로딩 상태 추가
  const buttonsPerGroup = 10; // 한 그룹에 보여줄 페이지 번호 수
  const router = useRouter(); // useRouter 사용

  // 데이터 로딩 중 처리
  useEffect(() => {
    if (results.length === 0) {
      setLoading(true); // 로딩 시작
    } else {
      setLoading(false); // 로딩 완료
    }
  }, [results]);

  // 현재 페이지의 웹툰 목록을 계산 (수정된 부분)
  const currentWebtoons = results;

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
    onPageChange(pageNumber - 1); // 부모 컴포넌트의 페이지 변경 함수 호출
  };

  // 다음 페이지 그룹으로 이동
  const handleNextGroup = () => {
    if ((pageGroup + 1) * buttonsPerGroup < totalPages) {
      setPageGroup(pageGroup + 1); // 페이지 그룹 업데이트
    }
  };

  // 이전 페이지 그룹으로 이동
  const handlePrevGroup = () => {
    if (pageGroup > 0) {
      setPageGroup(pageGroup - 1); // 페이지 그룹 업데이트
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
            <styles.LoadingMessage>로딩 중...</styles.LoadingMessage>
          ) : currentWebtoons.length > 0 ? (
            currentWebtoons.map((webtoon) => (
              <styles.WebtoonCard
                key={webtoon.id}
                onClick={() => handleWebtoonClick(webtoon.id)}
              >
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
            <p>웹툰이 없습니다.</p>
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
              isActive={currentPage === pageNumber - 1}
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
