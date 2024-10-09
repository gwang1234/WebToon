import { useEffect, useState } from "react";
import axios from "axios";
import * as styles from "../styles/CommentListStyled"; // 스타일 파일 가져오기

interface Comment {
  id: number;
  userName: string;
  content: string;
  provider_id: string | null;
  email: string | null;
}

interface CommentListProps {
  webtoonId: string;
  refresh: boolean; // 댓글 목록을 다시 불러오기 위한 트리거
}

export default function CommentList({ webtoonId, refresh }: CommentListProps) {
  const [comments, setComments] = useState<Comment[]>([]);
  const [page, setPage] = useState<number>(0);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [hasMore, setHasMore] = useState<boolean>(true);

  const fetchComments = async () => {
    setLoading(true);
    try {
      const response = await axios.get(
        `${process.env.NEXT_PUBLIC_API_URL}/reviews/${webtoonId}?page=${page}`
      );
      const newComments = response.data.content;

      if (newComments.length === 0) {
        setHasMore(false);
      } else {
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
  }, [webtoonId, page, refresh]); // refresh 값이 바뀌면 댓글을 다시 불러옴

  const loadMoreComments = () => {
    setPage((prev) => prev + 1);
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
