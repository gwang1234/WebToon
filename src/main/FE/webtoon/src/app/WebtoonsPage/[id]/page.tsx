"use client"; // 클라이언트 사이드에서만 렌더링됨

import WebtoonDetail from "../components/WebtoonDetail";
import CommentForm from "./components/CommentForm";
import * as styles from "../styles/mainStyles";
import CommentList from "./components/CommentList";

// WebtoonPage 컴포넌트는 `params`에서 id를 받아옴
interface WebtoonPageProps {
  params: {
    id: string;
  };
}

const WebtoonPage = ({ params }: WebtoonPageProps) => {
  const { id } = params;

  return (
    <styles.Container>
      <WebtoonDetail id={id} />
      <CommentForm webtoonId={id} />
      <CommentList webtoonId={id} />
    </styles.Container>
  );
};

export default WebtoonPage;
