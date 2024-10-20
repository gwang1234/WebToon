import styled from "styled-components";

// 댓글 폼 전체 컨테이너
export const CommentContainer = styled.div`
  margin-top: 10%;
  padding: 10px;
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
