import styled from "styled-components";

export const CommentContainer = styled.div`
  margin-top: 20px;
  padding: 10px;
  border-top: 1px solid #ccc;
  margin-bottom: 10%;
`;

export const CommentItem = styled.div`
  padding: 10px;
  border-bottom: 1px solid #ddd;
`;

export const CommentUser = styled.span`
  font-weight: bold;
  display: block;
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
