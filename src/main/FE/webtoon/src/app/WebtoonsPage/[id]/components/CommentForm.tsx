"use client";

import { useState } from "react";
import axios from "axios";
import {
  CommentContainer,
  CommentTextarea,
  SubmitButton,
  ErrorMessage,
  SuccessMessage,
} from "../styles/componentsStyled"; // 스타일 컴포넌트 가져오기

interface CommentFormProps {
  webtoonId: string;
}

export default function CommentForm({ webtoonId }: CommentFormProps) {
  const [comment, setComment] = useState<string>(""); // 댓글 상태
  const [star, setStar] = useState<number>(0); // 별점 상태 (1-5)
  const [error, setError] = useState<string | null>(null); // 에러 상태
  const [success, setSuccess] = useState<boolean>(false); // 성공 여부 상태

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const token = sessionStorage.getItem("token");
      const providerId = sessionStorage.getItem("provider_id") || ""; // provider_id가 null이면 빈 문자열로 설정

      if (!token) {
        setError("로그인 후 댓글을 작성할 수 있습니다.");
        return;
      }

      if (star < 1 || star > 5) {
        setError("별점은 1부터 5까지 선택해야 합니다.");
        return;
      }

      const response = await axios.post(
        `${process.env.NEXT_PUBLIC_API_URL}/reviews`, // webtoonId와 함께 provider_id, 별점, email도 포함
        {
          content: comment,
          webtoonId: webtoonId,
          provider_id: providerId,
          star: star, // 별점 전송
        },
        {
          headers: {
            Authorization: `Bearer ${token}`, // 토큰을 헤더에 추가
          },
        }
      );

      if (response.status === 201) {
        setSuccess(true);
        setComment(""); // 댓글 내용 초기화
        setStar(0); // 별점 초기화
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
        <div>
          <label>별점: </label>
          <select
            value={star}
            onChange={(e) => setStar(Number(e.target.value))} // 별점 값 설정
            required
          >
            <option value="0">별점을 선택하세요</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
          </select>
        </div>
        <SubmitButton type="submit">댓글 작성하기</SubmitButton>
      </form>
      {error && <ErrorMessage>{error}</ErrorMessage>}
      {success && <SuccessMessage>댓글 작성 완료!</SuccessMessage>}
    </CommentContainer>
  );
}
