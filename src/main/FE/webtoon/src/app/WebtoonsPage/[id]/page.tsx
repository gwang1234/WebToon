"use client"; // 클라이언트 사이드에서만 렌더링됨

import { useState } from "react";
import WebtoonDetail from "../components/WebtoonDetail";
import CommentForm from "./components/CommentForm";
import * as styles from "../styles/mainStyles";
import CommentList from "./components/CommentList";

interface WebtoonPageProps {
  params: {
    id: string;
  };
}

const WebtoonPage = ({ params }: WebtoonPageProps) => {
  const { id } = params;
  const [refreshComments, setRefreshComments] = useState<boolean>(false);

  // 댓글 목록을 다시 불러오는 트리거
  const handleCommentSubmit = () => {
    setRefreshComments((prev) => !prev);
  };

  return (
    <styles.Container>
      <WebtoonDetail id={id} />
      <CommentForm webtoonId={id} onCommentSubmit={handleCommentSubmit} />
      <CommentList webtoonId={id} refresh={refreshComments} />
    </styles.Container>
  );
};

export default WebtoonPage;
