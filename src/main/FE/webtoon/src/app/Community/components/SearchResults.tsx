import React from "react";
import * as styles from "../styles/mainStyles";
import Link from "next/link";

interface Community {
  id: number;
  title: string;
  content: string;
  view: number;
  createdAt: number;
}

interface CommunityListProps {
  communities: Community[];
}

const CommunityList: React.FC<CommunityListProps> = ({ communities }) => {
  return (
    <styles.CommunityList>
      <styles.CommunityName>
        <h3>제목</h3>
        <p>내용</p>
        <p>조회수</p>
        <p>작성일</p>
      </styles.CommunityName>

      {communities.length > 0 ? (
        communities.map((community) => (
          <Link href={`/Community/${community.id}`} key={community.id}>
            <styles.CommunityItem>
              <h3>{community.title}</h3>
              <p>{community.content}</p>
              <p>{community.view}</p>
              <p>{community.createdAt}</p>
            </styles.CommunityItem>
          </Link>
        ))
      ) : (
        <styles.EmptyMessage>커뮤니티 데이터가 없습니다.</styles.EmptyMessage>
      )}
    </styles.CommunityList>
  );
};

export default CommunityList;
