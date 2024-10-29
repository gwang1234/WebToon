"use client";

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
  const loggedInUserName = sessionStorage.getItem("userName") || "";
  const [editingCommentId, setEditingCommentId] = useState<number | null>(null); // 수정 중인 댓글 ID
  const [editedContent, setEditedContent] = useState<string>(""); // 수정 내용
  const [star, setStar] = useState<number>(0);

  const handleStarClick = (rating: number, event: React.MouseEvent) => {
    const { clientX, currentTarget } = event;
    const targetRect = currentTarget.getBoundingClientRect();
    const isHalf = clientX < targetRect.left + targetRect.width / 2;

    setStar(isHalf ? rating - 0.5 : rating); // 별의 왼쪽 클릭 시 0.5, 오른쪽 클릭 시 1
  };

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
        setComments(() => [...newComments]);
      }
    } catch (error) {
      setError("댓글을 불러오는 중 오류가 발생했습니다.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchComments();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [webtoonId, page, refresh]);

  const loadMoreComments = () => {
    setPage((prev) => prev + 1);
  };

  const handleEditClick = (comment: Comment) => {
    setEditingCommentId(comment.id);
    setEditedContent(comment.content);
    // console.log("수정하려는 댓글:", comment); // 수정하려는 댓글 로그
  };

  const handleUpdateComment = async (id: number) => {
    try {
      const token = sessionStorage.getItem("token"); // 세션 스토리지에서 토큰 가져오기
      const provider_id = sessionStorage.getItem("provider_id");

      // token이 있을 경우 Authorization 헤더 추가
      const headers = token ? { Authorization: `Bearer ${token}` } : {};

      // 서버로 수정된 댓글 정보 전송
      const response = await axios.put(
        `${process.env.NEXT_PUBLIC_API_URL}/reviews/${id}`,
        {
          webtoonId: id, // 댓글 ID
          userId: 0,
          content: editedContent, // 수정된 댓글 내용
          star: star,
          provider_id: provider_id, // provider_id
        },
        {
          headers,
        }
      );

      // 서버 응답이 성공적일 경우, 로컬 상태 업데이트
      if (response.status === 200) {
        setComments((prev) =>
          prev.map((comment) =>
            comment.id === id
              ? { ...comment, content: editedContent, star: star } // 수정된 내용과 별점 업데이트
              : comment
          )
        );
        setEditingCommentId(null); // 수정 상태 초기화
      }
    } catch (error) {
      setError("댓글 수정 중 오류가 발생했습니다.");
    }
  };

  // 댓글 삭제 함수
  const handleDeleteComment = async (id: number) => {
    const confirmDelete = window.confirm("정말로 이 댓글을 삭제하시겠습니까?");

    if (!confirmDelete) {
      return; // 사용자가 취소를 선택하면 삭제를 중단합니다.
    }

    try {
      const token = sessionStorage.getItem("token"); // 세션 스토리지에서 토큰 가져오기
      const providerId = sessionStorage.getItem("provider_id") || ""; // provider_id가 없으면 빈 문자열로 설정

      // token이 있을 경우 Authorization 헤더 추가
      const headers = token ? { Authorization: `Bearer ${token}` } : {};

      await axios.delete(`${process.env.NEXT_PUBLIC_API_URL}/reviews/${id}`, {
        headers,
        data: {
          provider_id: providerId, // provider_id 추가
        },
      });

      // 댓글 삭제 후 댓글 목록을 다시 가져오거나 상태 업데이트
      setComments((prev) => prev.filter((comment) => comment.id !== id));
      // console.log("댓글 삭제 성공:", { id }); // 삭제 성공 로그
    } catch (error) {
      setError("댓글 삭제 중 오류가 발생했습니다.");
      // console.error(error);
    }
  };

  return (
    <styles.CommentContainer>
      {comments.length === 0 && !loading && <p>댓글이 없습니다.</p>}
      {comments.map((comment, commentIndex) => (
        <styles.CommentItem key={`comment-${comment.id}-${commentIndex}`}>
          {editingCommentId === comment.id ? (
            <div>
              <styles.StarContainer>
                {[1, 2, 3, 4, 5].map((rating) => (
                  <styles.StarIcon
                    key={`star-${rating}`}
                    onClick={(e) => handleStarClick(rating, e)}
                    isFilled={star >= rating}
                    isHalfFilled={star >= rating - 0.5 && star < rating}
                  />
                ))}
              </styles.StarContainer>
              <input
                type="text"
                value={editedContent}
                onChange={(e) => setEditedContent(e.target.value)}
              />
              <styles.CommentStar
                onClick={() => handleUpdateComment(comment.id)}
              >
                수정
              </styles.CommentStar>
              <styles.CommentStar onClick={() => setEditingCommentId(null)}>
                취소
              </styles.CommentStar>
            </div>
          ) : (
            <>
              <styles.StarRating>
                {Array.from({ length: 5 }, (_, index) => {
                  const ratingValue = index + 1;
                  return (
                    <styles.StarIcon
                      key={`comment-${comment.id}-star-${index}-${commentIndex}`}
                      isFilled={comment.star >= ratingValue}
                      isHalfFilled={
                        comment.star >= ratingValue - 0.5 &&
                        comment.star < ratingValue
                      }
                    />
                  );
                })}
              </styles.StarRating>
              <styles.CommentContent>{comment.content}</styles.CommentContent>
              <styles.CommentContentUser>
                <styles.CommentUser>{comment.userName}</styles.CommentUser>
                {/* 로그인한 사용자와 댓글 작성자가 같을 때만 수정 및 삭제 버튼을 보여줌 */}
                {loggedInUserName === comment.userName ? (
                  <>
                    <styles.CommentStar
                      onClick={() => handleEditClick(comment)}
                    >
                      수정
                    </styles.CommentStar>
                    <styles.CommentStar
                      onClick={() => handleDeleteComment(comment.id)}
                    >
                      삭제
                    </styles.CommentStar>
                  </>
                ) : (
                  <></> // 버튼 숨기기
                )}
              </styles.CommentContentUser>
            </>
          )}
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
