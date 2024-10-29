import styled from "styled-components";

export const FormContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin: 1% auto;
  width: 50%;
  height: 100%;
`;

export const FormTitleSection = styled.div`
  display: flex;
  flex-direction: column;
  border-bottom: 1px solid #999;
  padding-bottom: 1rem; /* 여백 추가 */
`;

export const TitleInput = styled.textarea`
  font-size: 2rem;
  margin: 2% 0 3% 0;
  width: 100%; /* 전체 너비 사용 */
  padding: 0.5rem; /* 입력 필드 패딩 */
  border: 1px solid #ccc; /* 더 명확한 경계선 */
  border-radius: 4px;
  resize: none; /* 사용자가 높이를 조절할 수 있게 함 */
  overflow: hidden; /* 스크롤바 숨기기 */
`;

export const ContentSection = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 1.5rem; /* 상단 여백 추가 */
`;

export const ContentInput = styled.textarea`
  font-size: 1rem;
  margin: 2% 0 3% 0;
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  height: 200px; /* 기본 높이 설정 */
  resize: none; /* 사용자가 높이를 조절할 수 있게 함 */
  overflow: hidden; /* 스크롤바 숨기기 */
`;

export const SubmitButton = styled.button`
  display: inline-block;
  padding: 0.75rem 1.5rem;
  margin-top: 1.5rem;
  font-size: 1rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  text-align: center;

  &:hover {
    background-color: #0056b3;
  }
`;
