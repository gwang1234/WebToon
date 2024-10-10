"use client";

import { useState } from "react";
import axios from "axios";
import {
  CommentContainer,
  CommentTextarea,
  SubmitButton,
  ErrorMessage,
  SuccessMessage,
  StarContainer,
  StarIcon, // 별 아이콘 스타일 추가
} from "../styles/componentsStyled"; // 스타일 파일에 별 아이콘 관련 스타일을 추가해야 함

interface CommentFormProps {
  webtoonId: string;
  onCommentSubmit: () => void; // 댓글 작성 후 호출되는 함수
}

export default function CommentForm({
  webtoonId,
  onCommentSubmit,
}: CommentFormProps) {
  const [comment, setComment] = useState<string>("");
  const [star, setStar] = useState<number>(0);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<boolean>(false);

  // 별 클릭 시 별점 반영 함수
  const handleStarClick = (rating: number) => {
    setStar(rating); // 클릭한 별의 값으로 별점 설정
    setError(null); // 오류 메시지 초기화
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const token = sessionStorage.getItem("token");
      const providerId = sessionStorage.getItem("provider_id") || "";

      if (!token) {
        setError("로그인 후 댓글을 작성할 수 있습니다.");
        return;
      }

      if (star < 1 || star > 5) {
        setError("별점은 1부터 5까지 선택해야 합니다.");
        return;
      }

      const response = await axios.post(
        `${process.env.NEXT_PUBLIC_API_URL}/reviews`,
        {
          content: comment,
          webtoonId: webtoonId,
          provider_id: providerId,
          star: star,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (response.status === 201) {
        setSuccess(true);
        setComment("");
        setStar(0);
        onCommentSubmit(); // 댓글 작성 후 댓글 목록을 다시 불러옴
      }
    } catch (error) {
      setError("댓글 작성 중 오류가 발생했습니다.");
    }
  };

  return (
    <CommentContainer>
      <form onSubmit={handleSubmit}>
        <CommentTextarea
          value={comment}
          onChange={(e) => setComment(e.target.value)}
          placeholder="댓글을 입력하세요"
          required
        />
        <StarContainer>
          {/* 별점 클릭을 위한 별 아이콘들 */}
          {[1, 2, 3, 4, 5].map((rating) => (
            <StarIcon
              key={rating}
              onClick={() => handleStarClick(rating)} // 클릭 시 별점 설정
              className={star >= rating ? "like-on" : "like-off"} // 별점에 따라 클래스 변경
            />
          ))}
        </StarContainer>
        <SubmitButton type="submit">댓글 작성하기</SubmitButton>
      </form>
      {error && <ErrorMessage>{error}</ErrorMessage>}
      {success && <SuccessMessage>댓글 작성 완료!</SuccessMessage>}
    </CommentContainer>
  );
}
