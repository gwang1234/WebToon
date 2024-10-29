import styled from "styled-components";

// 댓글 폼 전체 컨테이너
export const CommentContainer = styled.div`
  margin-top: 10%;
  padding: 1%;
  width: 60%;
  transform: translateX(-50%);
  margin-left: 50%;
  border-top: 1px solid #ccc;
`;

// 텍스트 입력 필드 스타일
export const CommentTextarea = styled.textarea`
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 5px;
  border: 1px solid #ddd;
  font-size: 16px;
  resize: vertical;
  min-height: 80px;

  &:focus {
    border-color: #888;
    outline: none;
  }
`;

// 버튼 스타일
export const SubmitButton = styled.button`
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;

  &:hover {
    background-color: #0056b3;
  }
`;

// 에러 메시지 스타일
export const ErrorMessage = styled.p`
  color: red;
  font-size: 14px;
  margin-top: 10px;
`;

// 성공 메시지 스타일
export const SuccessMessage = styled.p`
  color: green;
  font-size: 14px;
  margin-top: 10px;
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
