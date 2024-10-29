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
  const [refresh, setRefresh] = useState<boolean>(false);

  const handleCommentSubmit = () => {
    console.log("댓글 작성 완료, refresh 상태 변경됨");
    setRefresh((prev) => !prev);
  };

  return (
    <styles.Container>
      <WebtoonDetail id={id} />
      <CommentForm webtoonId={id} onCommentSubmit={handleCommentSubmit} />
      <CommentList webtoonId={id} refresh={refresh} />
    </styles.Container>
  );
};

export default WebtoonPage;
