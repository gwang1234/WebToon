import * as styles from "../../styles/detailStyles";

interface CommunityHeaderProps {
  title: string;
  userName: string;
  createdAt: string;
  updatedAt: string;
  view: number;
  likeCount: number;
}

export default function CommunityHeader({
  title,
  userName,
  createdAt,
  updatedAt,
  view,
  likeCount,
}: CommunityHeaderProps) {
  return (
    <>
      <styles.Title>{title}</styles.Title>
      <styles.NameAndDate>
        <styles.Name>작성자: {userName}</styles.Name>
        {createdAt === updatedAt ? (
          <styles.Date>작성일: {createdAt}</styles.Date>
        ) : (
          <styles.Date>수정일: {updatedAt}</styles.Date>
        )}
        <styles.Views>조회수: {view}</styles.Views>
        <styles.Views>추천: {likeCount}</styles.Views>
      </styles.NameAndDate>
    </>
  );
}
