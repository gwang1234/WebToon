import styled from "styled-components";
import { fonts } from "@/app/fonts/fonts";

export const CommentContainer = styled.div`
  display: flex;
  flex-direction: column;
  padding: 10px;
  border-top: 1px solid #ccc;
  margin-bottom: 10%;
  width: 60%;
  transform: translateX(-50%);
  margin-left: 50%;
  font-family: ${fonts.roboto};
`;

export const CommentItem = styled.div`
  padding: 10px;
  border-bottom: 1px solid #ddd;
`;

export const CommentUser = styled.span`
  font-size: 15px;
  color: #888;
  font-weight: bold;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const CommentDate = styled.span`
  font-size: 12px;
  color: #888;
  display: block;
`;

export const CommentContent = styled.p`
  font-size: 16px;
  margin: 0.8rem 0%;
`;

export const CommentContentUser = styled.div`
  display: flex;
  margin-bottom: 1rem;
`;

export const CommentStar = styled.p`
  font-size: 15px;
  display: inline-flex;
  color: #888;
  cursor: pointer;

  ::before {
    content: "·";
    font-size: 20px; /* 점 크기 조정 */
    margin: 0 5px; /* 점과 텍스트 사이 여백 조정 */
  }
`;

export const LoadMoreButton = styled.button`
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin-top: 10px;

  &:hover {
    background-color: #0056b3;
  }
`;

export const StarRating = styled.div`
  display: flex;
  margin-top: 1rem;
`;

export const StarContainer = styled.div`
  display: flex;
  margin: 1%;
`;

interface StarIconProps {
  isFilled: boolean;
  isHalfFilled: boolean;
}

export const StarIcon = styled.div<StarIconProps>`
  width: 24px;
  height: 24px;
  cursor: pointer;
  background-image: url("/like-off.png");
  background-size: contain;
  background-repeat: no-repeat;
  position: relative;

  /* 왼쪽 절반 채우기 */
  &::before {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-image: url("/like-on.png"); /* 채워진 별 이미지 */
    background-size: contain;
    background-repeat: no-repeat;
    clip-path: ${({ isHalfFilled, isFilled }) =>
      isHalfFilled
        ? "inset(0 50% 0 0)"
        : isFilled
        ? "inset(0)"
        : "inset(0 100% 0 0)"};
  }
`;
