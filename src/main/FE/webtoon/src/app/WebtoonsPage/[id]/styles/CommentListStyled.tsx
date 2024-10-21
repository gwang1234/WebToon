import styled from "styled-components";

export const CommentContainer = styled.div`
  margin-top: 20px;
  padding: 10px;
  border-top: 1px solid #ccc;
  margin-bottom: 10%;
  width: 60%;
  transform: translate(-50%, -50%);
  margin-left: 50%;
`;

export const CommentItem = styled.div`
  padding: 10px;
  border-bottom: 1px solid #ddd;
`;

export const CommentUser = styled.span`
  font-weight: bold;
`;

export const CommentDate = styled.span`
  font-size: 12px;
  color: #888;
  display: block;
`;

export const CommentContent = styled.p`
  margin-top: 5px;
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
`;

export const StarIcon = styled.div`
  width: 16px;
  height: 16px;
  margin-right: 4px;
  background-size: contain;

  &.like-on {
    background-image: url("/like-on.png"); // 활성화된 별 이미지 경로
  }

  &.like-off {
    background-image: url("/like-off.png"); // 비활성화된 별 이미지 경로
  }
`;
