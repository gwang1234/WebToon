import * as styles from "../../styles/detailStyles";

interface CommunityContentProps {
  content: string;
}

export default function CommunityContent({ content }: CommunityContentProps) {
  return <styles.Content>{content}</styles.Content>;
}
