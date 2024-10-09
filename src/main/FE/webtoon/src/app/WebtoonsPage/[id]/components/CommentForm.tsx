"use client";

import { useState } from "react";
import axios from "axios";
import {
  CommentContainer,
  CommentTextarea,
  SubmitButton,
  ErrorMessage,
  SuccessMessage,
} from "../styles/componentsStyled";

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
        <div>
          <label>별점: </label>
          <select
            value={star}
            onChange={(e) => setStar(Number(e.target.value))}
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
