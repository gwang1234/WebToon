import { useEffect, useState } from "react";
import axios from "axios";
import * as styles from "../styles/CommentListStyled"; // 스타일 파일 가져오기

interface Comment {
  id: number;
  userName: string;
  content: string;
  provider_id: string | null;
  email: string | null;
  star: number; // 별점 필드 추가
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
    console.log("Fetching comments..."); // 추가된 로그
    setLoading(true);
    try {
      const response = await axios.get(
        `${process.env.NEXT_PUBLIC_API_URL}/reviews/${webtoonId}?page=${page}`
      );
      const newComments = response.data.content;
      console.log("Fetched comments:", newComments); // 추가된 로그

      if (newComments.length === 0) {
        setHasMore(false);
        console.log("No more comments to load."); // 추가된 로그
      } else {
        setComments((prev) => [...prev, ...newComments]);
      }
    } catch (error) {
      setError("댓글을 불러오는 중 오류가 발생했습니다.");
      console.error("Error fetching comments:", error); // 추가된 로그
    } finally {
      setLoading(false);
      console.log("Loading state set to false."); // 추가된 로그
    }
  };

  useEffect(() => {
    fetchComments();
  }, [webtoonId, page, refresh]); // refresh 값이 바뀌면 댓글을 다시 불러옴

  const loadMoreComments = () => {
    console.log("Loading more comments..."); // 추가된 로그
    setPage((prev) => prev + 1);
  };

  return (
    <styles.CommentContainer>
      {comments.length === 0 && !loading && <p>댓글이 없습니다.</p>}
      {comments.map((comment) => (
        <styles.CommentItem key={comment.id}>
          <styles.CommentUser>{comment.userName}</styles.CommentUser>{" "}
          {/* 댓글 작성자 이름 */}
          <styles.CommentContent>{comment.content}</styles.CommentContent>
          <styles.StarRating>
            {Array.from({ length: 5 }, (_, index) => (
              <styles.StarIcon
                key={index}
                className={index < comment.star ? "like-on" : "like-off"}
              />
            ))}
          </styles.StarRating>
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
