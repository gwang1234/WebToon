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
  communityId: string;
  onCommentSubmit: () => void; // 댓글 작성 성공 시 호출할 함수
}

export default function CommentForm({
  communityId,
  onCommentSubmit,
}: CommentFormProps) {
  const [comment, setComment] = useState<string>(""); // 댓글 상태
  const [error, setError] = useState<string | null>(null); // 에러 상태
  const [success, setSuccess] = useState<boolean>(false); // 성공 여부 상태

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const token = sessionStorage.getItem("token");

      if (!token) {
        setError("로그인 후 댓글을 작성할 수 있습니다.");
        return;
      }

      const response = await axios.post(
        `${process.env.NEXT_PUBLIC_API_URL}/c-comment/write/${communityId}`,
        { content: comment }, // 댓글 내용 전송
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (response.status === 201) {
        setSuccess(true);
        setComment(""); // 댓글 내용 초기화
        onCommentSubmit(); // 댓글 작성 후 부모 컴포넌트에 알림
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
        <SubmitButton type="submit">댓글 작성하기</SubmitButton>
      </form>
      {error && <ErrorMessage>{error}</ErrorMessage>}
      {success && <SuccessMessage>댓글 작성 완료!</SuccessMessage>}
    </CommentContainer>
  );
}
