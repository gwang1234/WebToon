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
  const [editingCommentId, setEditingCommentId] = useState<number | null>(null); // 수정 중인 댓글 ID
  const [editedContent, setEditedContent] = useState<string>(""); // 수정 내용

  useEffect(() => {
    const fetchComments = async () => {
      setLoading(true);
      try {
        const response = await axios.get(
          `${process.env.NEXT_PUBLIC_API_URL}/c-comment/${communityId}?page=${page}`
        );
        const newComments = response.data.content;
        // console.log("새로 불러온 댓글:", newComments); // 불러온 댓글 로그

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

    fetchComments();
  }, [communityId, page]);

  const loadMoreComments = () => {
    // console.log("다음 페이지 로드 요청:", page + 1); // 다음 페이지 로드 요청 로그
    setPage((prev) => prev + 1); // 다음 페이지로 넘어가기
  };

  const handleEditClick = (comment: Comment) => {
    setEditingCommentId(comment.id);
    setEditedContent(comment.content);
    // console.log("수정하려는 댓글:", comment); // 수정하려는 댓글 로그
  };

  const handleUpdateComment = async (id: number) => {
    // console.log("댓글 수정 요청:", { id, content: editedContent }); // 수정 요청 로그
    try {
      const token = sessionStorage.getItem("token"); // 세션 스토리지에서 토큰 가져오기
      const commentToUpdate = comments.find((comment) => comment.id === id);

      // provider_id가 없으면 빈 문자열로 설정
      const providerId = commentToUpdate?.provider_id || "";

      // 서버로 수정된 댓글 정보 전송
      const response = await axios.put(
        `${process.env.NEXT_PUBLIC_API_URL}/c-comment/update/${id}`,
        {
          id: id, // 댓글 ID
          content: editedContent, // 수정된 댓글 내용
          provider_id: providerId, // provider_id
        },
        {
          headers: {
            Authorization: `Bearer ${token}`, // 토큰을 헤더에 추가
          },
        }
      );

      // 서버 응답이 성공적일 경우, 로컬 상태 업데이트
      if (response.status === 200) {
        setComments((prev) =>
          prev.map((comment) =>
            comment.id === id ? { ...comment, content: editedContent } : comment
          )
        );
        // console.log("댓글 수정 성공:", { id, content: editedContent }); // 수정 성공 로그
        setEditingCommentId(null); // 수정 상태 초기화
      }
    } catch (error) {
      setError("댓글 수정 중 오류가 발생했습니다.");
      console.error(error);
    }
  };

  // 댓글 삭제 함수
  const handleDeleteComment = async (id: number) => {
    // console.log("댓글 삭제 요청:", { id }); // 삭제 요청 로그
    try {
      const token = sessionStorage.getItem("token"); // 세션 스토리지에서 토큰 가져오기
      const providerId = sessionStorage.getItem("provider_id") || ""; // provider_id가 없으면 빈 문자열로 설정

      await axios.delete(
        `${process.env.NEXT_PUBLIC_API_URL}/c-comment/delete/${id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`, // 토큰을 헤더에 추가
          },
          data: {
            provider_id: providerId, // provider_id 추가
          },
        }
      );

      // 댓글 삭제 후 댓글 목록을 다시 가져오거나 상태 업데이트
      setComments((prev) => prev.filter((comment) => comment.id !== id));
      // console.log("댓글 삭제 성공:", { id }); // 삭제 성공 로그
    } catch (error) {
      setError("댓글 삭제 중 오류가 발생했습니다.");
      console.error(error);
    }
  };

  return (
    <styles.CommentContainer>
      {comments.length === 0 && !loading && <p>댓글이 없습니다.</p>}
      {comments.map((comment) => (
        <styles.CommentItem key={comment.id}>
          <styles.CommentUser>{comment.userName}</styles.CommentUser>
          {editingCommentId === comment.id ? (
            <div>
              <input
                type="text"
                value={editedContent}
                onChange={(e) => setEditedContent(e.target.value)}
              />
              <button onClick={() => handleUpdateComment(comment.id)}>
                수정
              </button>
              <button onClick={() => setEditingCommentId(null)}>취소</button>
            </div>
          ) : (
            <>
              <styles.CommentContent>{comment.content}</styles.CommentContent>
              <button onClick={() => handleEditClick(comment)}>수정</button>
              <button onClick={() => handleDeleteComment(comment.id)}>
                삭제
              </button>{" "}
              {/* 삭제 버튼 추가 */}
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
