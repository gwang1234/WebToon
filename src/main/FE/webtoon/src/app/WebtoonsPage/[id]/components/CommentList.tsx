import { useEffect, useState } from "react";
import axios from "axios";
import * as styles from "../styles/CommentListStyled"; // 스타일 파일 가져오기

interface Comment {
  id: number;
  userName: string; // 댓글 작성자 정보
  content: string;
  provider_id: string | null; // provider_id 추가
  email: string | null; // 이메일 추가
}

interface CommentListProps {
  communityId: string; // 커뮤니티 ID를 props로 받음
}

export default function CommentList({ communityId }: CommentListProps) {
  const [comments, setComments] = useState<Comment[]>([]); // 댓글 목록 상태
  const [page, setPage] = useState<number>(0); // 페이지 상태
  const [loading, setLoading] = useState<boolean>(false); // 로딩 상태
  const [error, setError] = useState<string | null>(null); // 에러 상태
  const [hasMore, setHasMore] = useState<boolean>(true); // 더 가져올 댓글이 있는지 여부

  const fetchComments = async () => {
    setLoading(true);
    try {
      const response = await axios.get(
        `${process.env.NEXT_PUBLIC_API_URL}/c-comment/${communityId}?page=${page}`
      );
      const newComments = response.data.content;
      console.log("새로 불러온 댓글:", newComments); // 불러온 댓글 로그

      if (newComments.length === 0) {
        setHasMore(false); // 더 이상 가져올 댓글이 없는 경우
      } else {
        // 기존 댓글 배열에 새로운 댓글 추가
        setComments((prev) => [...prev, ...newComments]);
      }
    } catch (error) {
      setError("댓글을 불러오는 중 오류가 발생했습니다.");
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchComments();
  }, [communityId, page]);

  const loadMoreComments = () => {
    console.log("다음 페이지 로드 요청:", page + 1); // 다음 페이지 로드 요청 로그
    setPage((prev) => prev + 1); // 다음 페이지로 넘어가기
  };

  return (
    <styles.CommentContainer>
      {comments.length === 0 && !loading && <p>댓글이 없습니다.</p>}
      {comments.map((comment) => (
        <styles.CommentItem key={comment.id}>
          <styles.CommentUser>{comment.userName}</styles.CommentUser>
          <styles.CommentContent>{comment.content}</styles.CommentContent>
        </styles.CommentItem>
      ))}
      {error && <p style={{ color: "red" }}>{error}</p>}
      {loading && <p>로딩 중...</p>}
      {hasMore && !loading && comments.length >= 20 && (
        <styles.LoadMoreButton onClick={loadMoreComments}>
          더 보기
        </styles.LoadMoreButton>
      )}
    </styles.CommentContainer>
  );
}
