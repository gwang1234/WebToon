"use client"; // 클라이언트 컴포넌트로 지정

import React, { useEffect, useState } from "react";
import axios from "axios";
import Image from "next/image";
import * as styles from "../styles/WebtoonDetailPropsStyles"; // styled-components 스타일 임포트

// Webtoon 타입 정의
type Webtoon = {
  id: number;
  title: string;
  provider: string;
  views: number;
  author: string;
  imageUrl: string;
  description: string;
};

interface WebtoonDetailProps {
  id: string | string[] | undefined; // id는 string이나 배열일 수 있음
}

const Main: React.FC<WebtoonDetailProps> = ({ id }) => {
  const [webtoon, setWebtoon] = useState<Webtoon | null>(null);
  const [likeCount, setLikeCount] = useState<number>(0); // 좋아요 수 상태 추가
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  // 특정 웹툰 플랫폼으로 이동하는 함수
  const goToProvider = () => {
    if (!webtoon) return;

    let url = "";
    if (webtoon.provider === "네이버웹툰") {
      url = `https://comic.naver.com/index`;
    } else if (webtoon.provider === "카카오웹툰") {
      url = `https://webtoon.kakao.com/`;
    }

    if (url) {
      window.open(url, "_blank"); // 새 창으로 열기
    } else {
      alert("지원되지 않는 플랫폼입니다.");
    }
  };

  // 좋아요 API 호출 함수
  const fetchLikeCount = async (webtoonId: number) => {
    try {
      const userId = sessionStorage.getItem("userId"); // 세션에 저장된 사용자 ID 가져오기
      if (!userId) {
        return;
      }

      const response = await axios.get(
        `${process.env.NEXT_PUBLIC_API_URL}/webtoons/${webtoonId}/like`,
        {
          params: { id: userId }, // 사용자 ID를 쿼리 파라미터로 전달
        }
      );

      setLikeCount(response.data.webtoon_id); // 좋아요 수 업데이트
    } catch (error) {
      console.error("좋아요 수 가져오기 실패:", error);
      alert("좋아요 수를 가져오는 중 오류가 발생했습니다.");
    }
  };

  const like = async () => {
    if (!webtoon) return;

    try {
      const token = sessionStorage.getItem("token"); // 세션에 저장된 토큰 가져오기

      if (!token) {
        alert("로그인이 필요합니다."); // 토큰이 없으면 로그인 필요
        return;
      }

      const userId = sessionStorage.getItem("userId"); // 세션에 저장된 사용자 ID 가져오기

      if (!userId) {
        alert("로그인이 필요합니다."); // 사용자 ID가 없을 경우 처리
        return;
      }

      const response = await axios.post(
        `${process.env.NEXT_PUBLIC_API_URL}/webtoons/${webtoon.id}/like`, // 좋아요 API 요청
        null,
        {
          headers: {
            Authorization: `Bearer ${token}`, // JWT 토큰을 헤더에 추가
          },
          params: {
            id: userId, // 요청에 사용자 ID를 전달
          },
        }
      );

      alert(response.data); // 서버의 응답 메시지를 보여줌
      fetchLikeCount(webtoon.id); // 좋아요 수를 다시 가져옴
    } catch (error) {
      console.error("좋아요 처리 중 오류가 발생했습니다:", error);
      alert("좋아요 처리 중 오류가 발생했습니다.");
    }
  };

  useEffect(() => {
    console.log("id 값:", id); // id 값이 무엇인지 확인
    if (id) {
      const webtoonId = Array.isArray(id) ? id[0] : id; // id가 배열이면 첫 번째 값을 사용
      const fetchWebtoonDetail = async () => {
        setLoading(true);
        try {
          const response = await axios.get(
            `${process.env.NEXT_PUBLIC_API_URL}/webtoons/${webtoonId}`
          );
          setWebtoon(response.data);

          // 웹툰 상세 정보를 가져온 후 좋아요 수를 가져옴
          fetchLikeCount(response.data.id);
        } catch (error) {
          console.error(
            "웹툰 상세 정보를 가져오는 중 오류가 발생했습니다:",
            error
          );
          setError("웹툰 정보를 불러오는 중 오류가 발생했습니다.");
        } finally {
          setLoading(false);
          console.log("확인");
        }
      };

      fetchWebtoonDetail();
    }
  }, [id]);

  if (loading) {
    return <styles.LoadingMessage>로딩 중...</styles.LoadingMessage>;
  }

  if (error) {
    return <styles.ErrorMessage>{error}</styles.ErrorMessage>;
  }

  return webtoon ? (
    <styles.Section>
      <styles.WebtoonDetailContainer>
        <styles.Images>
          <Image
            src={webtoon.imageUrl || "/default-image.jpg"}
            alt={webtoon.title}
            fill // fill 속성 사용
            sizes="(max-width: 768px) 100vw, (max-width: 1200px) 50vw, 33vw" // 반응형 사이즈 설정
            style={{ objectFit: "cover" }} // 이미지 비율 유지
            priority
          />
        </styles.Images>
        <styles.Information>
          <styles.WebtoonTitle>{webtoon.title}</styles.WebtoonTitle>
          <styles.WebtoonAuthor>글/그림: {webtoon.author}</styles.WebtoonAuthor>
          <styles.WebtoonViews>조회수: {webtoon.views}</styles.WebtoonViews>
          <styles.WebtoonDescription>
            {webtoon.description}
          </styles.WebtoonDescription>
          <styles.Buttons>
            <styles.GoToButton onClick={goToProvider}>
              {webtoon.provider} 웹툰 보러가기
            </styles.GoToButton>
            <styles.Like onClick={like}>
              관심 {likeCount} {/* 좋아요 수 출력 */}
            </styles.Like>
          </styles.Buttons>
        </styles.Information>
      </styles.WebtoonDetailContainer>
    </styles.Section>
  ) : (
    <p>웹툰 정보를 찾을 수 없습니다.</p>
  );
};

export default Main;
