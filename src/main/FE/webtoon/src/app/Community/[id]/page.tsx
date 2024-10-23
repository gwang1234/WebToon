"use client"; // 클라이언트 사이드에서만 렌더링됨

import { useEffect, useState } from "react";
import axios from "axios";
import * as styles from "../styles/detailStyles";
import CommunityHeader from "./components/CommunityHeader";
import CommunityContent from "./components/CommunityContent";
import LikeButton from "./components/LikeButton";
import CommentForm from "./components/CommentForm";
import CommentList from "./components/CommentList";
import CommunityDeleteButton from "./components/CommunityDeleteButton"; // 삭제 버튼 컴포넌트
import Link from "next/link";

interface CommunityDetail {
  id: number;
  userName: string;
  title: string;
  content: string;
  view: number;
  createdAt: string;
  updatedAt: string;
  likes: number;
}

interface CommunityDetailPageProps {
  params: {
    id: string;
  };
}

export default function CommunityDetailPage({
  params,
}: CommunityDetailPageProps) {
  const [community, setCommunity] = useState<CommunityDetail | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [reloadComments, setReloadComments] = useState<number>(0); // 댓글 목록 갱신 상태
  const [sessionUserName, setSessionUserName] = useState<string | null>(null); // 세션의 userName

  const { id } = params;

  useEffect(() => {
    const fetchCommunityDetail = async () => {
      setLoading(true);
      try {
        const response = await axios.get(
          `${process.env.NEXT_PUBLIC_API_URL}/community/detail/${id}`
        );
        setCommunity(response.data);
      } catch (error) {
        console.error("커뮤니티 상세 정보 가져오는 중 오류 발생:", error);
        setError("데이터를 불러오는 중 오류가 발생했습니다.");
      } finally {
        setLoading(false);
      }
    };

    // 세션에서 userName 가져오기
    const storedUserName = sessionStorage.getItem("userName");
    setSessionUserName(storedUserName);

    fetchCommunityDetail();
  }, [id]);

  if (loading) {
    return <p>로딩 중...</p>;
  }

  if (error) {
    return <p>{error}</p>;
  }

  return (
    <styles.Container>
      {community ? (
        <>
          <CommunityHeader
            title={community.title}
            userName={community.userName}
            createdAt={community.createdAt}
            updatedAt={community.updatedAt}
            view={community.view}
            likeCount={community.likes}
          />
          <CommunityContent content={community.content} />
          <LikeButton
            id={id}
            likeCount={community.likes}
            setError={setError}
            setLikeCount={function (): void {
              throw new Error("Function not implemented.");
            }}
          />

          {/* 수정하기 링크와 삭제 버튼은 현재 로그인한 userName과 작성자 userName이 동일할 때만 렌더링 */}
          {sessionUserName === community.userName && (
            <>
              <Link href={`/Community/${id}/update`}>
                <p>수정하기</p>
              </Link>
              <CommunityDeleteButton id={id} />
            </>
          )}

          {/* 댓글 작성 폼 추가 */}
          <CommentForm
            communityId={id}
            onCommentSubmit={() => setReloadComments(reloadComments + 1)}
          />

          {/* 댓글 목록 */}
          <CommentList communityId={id} key={reloadComments} />
        </>
      ) : (
        <styles.Details>커뮤니티 정보를 찾을 수 없습니다.</styles.Details>
      )}
    </styles.Container>
  );
}
