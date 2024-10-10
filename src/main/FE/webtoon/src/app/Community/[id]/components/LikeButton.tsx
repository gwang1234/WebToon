import { useState } from "react";
import Image from "next/image";
import axios from "axios";
import * as styles from "../../styles/detailStyles";

interface LikeButtonProps {
  id: string;
  likeCount: number;
  setLikeCount: (newCount: number) => void;
  setError: (error: string | null) => void;
}

export default function LikeButton({
  id,
  likeCount,
  setLikeCount,
  setError,
}: LikeButtonProps) {
  const [loading, setLoading] = useState(false);

  const handleLikeClick = async () => {
    try {
      const token = sessionStorage.getItem("token");

      if (!token) {
        setError("로그인 후 이용해주세요.");
        return;
      }

      setLoading(true);
      const response = await axios.post(
        `${process.env.NEXT_PUBLIC_API_URL}/community/like-set/${id}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      console.log("추천 요청 성공 응답 데이터:", response.data);
      setLikeCount(likeCount + 1);
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
