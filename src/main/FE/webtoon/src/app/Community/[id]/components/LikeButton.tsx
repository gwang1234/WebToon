"use client"; // 클라이언트 컴포넌트로 설정

import { useState } from "react";
import Image from "next/image";
import axios from "axios";
import * as styles from "../../styles/detailStyles";

interface LikeButtonProps {
  id: string;
  setError: (error: string | null) => void;
  onLike: () => void; // 상위 컴포넌트에 상태를 전달하는 콜백 함수
}

export default function LikeButton({ id, setError, onLike }: LikeButtonProps) {
  const [loading, setLoading] = useState(false);

  const handleLikeClick = async () => {
    try {
      const token = sessionStorage.getItem("token");
      const provider_id = sessionStorage.getItem("provider_id") || "";

      if (!token && !provider_id) {
        setError("로그인 후 이용해주세요.");
        return;
      }

      setLoading(true);

      const headers = token ? { Authorization: `Bearer ${token}` } : {};

      const response = await axios.post(
        `${process.env.NEXT_PUBLIC_API_URL}/community/like-set/${id}`,
        { provider_id: provider_id },
        { headers }
      );

      if (response.data.statusCode === 200) {
        onLike(); // 상위 컴포넌트에 상태 전달
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
      {loading}
    </styles.Good>
  );
}
