"use client"; // 클라이언트 컴포넌트로 설정

import { useState } from "react";
import Image from "next/image";
import axios from "axios";
import * as styles from "../../styles/detailStyles";

interface LikeButtonProps {
  id: string;
  updateLikeCount: (newLikeCount: number) => void;
  setError: (error: string | null) => void;
}

export default function LikeButton({
  id,
  updateLikeCount,
  setError,
}: LikeButtonProps) {
  const [loading, setLoading] = useState(false);

  // LikeButton 컴포넌트 내에서 좋아요 버튼 클릭 시 서버로 요청하고 새 좋아요 수를 업데이트하는 부분
  const handleLikeClick = async () => {
    try {
      const token = sessionStorage.getItem("token");
      const provider_id = sessionStorage.getItem("provider_id") || "";

      if (!token && !provider_id) {
        setError("로그인 후 이용해주세요.");
        return;
      }

      setLoading(true);

      // token이 있을 경우 Authorization 헤더 추가
      const headers = token ? { Authorization: `Bearer ${token}` } : {};

      // 서버로 추천 요청을 보내고 응답을 받음
      const response = await axios.post(
        `${process.env.NEXT_PUBLIC_API_URL}/community/like-set/${id}`, // 커뮤니티 게시물 ID
        { provider_id: provider_id },
        {
          headers,
        }
      );

      // 서버로부터 성공적으로 요청이 처리되었다면 좋아요 수를 업데이트
      if (response.data.statusCode === 200) {
        updateLikeCount(response.data.likes); // 서버에서 반환된 좋아요 수로 업데이트
        console.log("추천 요청 성공:", response.data.responseMessage);
      } else {
        setError("추천에 실패했습니다.");
      }
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error(
          "추천을 보내는 중 오류 발생 (상태 코드):",
          error.response?.status
        );
        console.error(
          "추천을 보내는 중 오류 발생 (응답 데이터):",
          error.response?.data
        );
      } else {
        console.error("추천을 보내는 중 알 수 없는 오류 발생:", error);
      }
      setError("추천을 처리하는 중 오류가 발생했습니다.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <styles.Good onClick={handleLikeClick}>
      <Image src="/good.svg" alt={"good"} fill />
      {loading && <span>로딩 중...</span>}
    </styles.Good>
  );
}
