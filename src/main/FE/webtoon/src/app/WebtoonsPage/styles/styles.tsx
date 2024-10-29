import styled from "styled-components";

// 웹툰 페이지의 전체 컨테이너
export const Section = styled.section`
  padding: 2%;
  width: 99%;
  max-width: 1200px;
  margin: 0 auto;
`;

// 웹툰 목록 그리드
export const WebtoonGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(
    auto-fill,
    minmax(15%, 1fr)
  ); /* 최소 너비 15% */
  gap: 3%;
  margin-bottom: 5%;
`;

// 웹툰 카드
export const WebtoonCard = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 5%;
  background-color: #f5f5f5;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s;
  cursor: pointer;

  &:hover {
    transform: translateY(-5px);
  }

  img {
    border-radius: 8px;
    width: 100%;
    height: auto;
  }

  p {
    overflow: hidden; /* 내용 넘침을 숨김 */
    text-overflow: ellipsis; /* 텍스트가 넘칠 때 ...로 표시 */
    white-space: nowrap; /* 텍스트가 한 줄로 표시 */
    max-width: 100%; /* 최대 너비 */
  }
`;

// 페이지네이션 컨테이너
export const PaginationContainer = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 15%;
`;

// 페이지네이션 버튼 스타일
export const PageButton = styled.button<{ isActive?: boolean }>`
  margin: 6% 0% 1% 1%;
  padding: 1% 2%;
  background-color: ${(props) => (props.isActive ? "#0070f3" : "#ccc")};
  color: ${(props) => (props.isActive ? "#fff" : "#000")};
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: ${(props) => (props.isActive ? "#005bb5" : "#bbb")};
  }

  &:disabled {
    background-color: #f0f0f0;
    cursor: not-allowed;
  }
`;

// 로딩 메시지 스타일
export const LoadingMessage = styled.p`
  text-align: center;
  font-size: 120%;
  color: #888;
`;

// 에러 메시지 스타일
export const ErrorMessage = styled.p`
  text-align: center;
  font-size: 120%;
  color: red;
`;

export const GenreButtonContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(11, 1fr); /* 11개의 열을 균등하게 배치 */
  justify-items: center; /* 버튼들을 가운데 정렬 */
  gap: 1vh; /* 가로 세로 간격을 동일하게 설정 (px 단위로 고정) */
  width: 60%;
  margin-top: 3%;
  margin-bottom: 1%;
  margin-left: 50%;
  transform: translateX(-50%);
`;

// 장르 선택 버튼 스타일
export const GenreButton = styled.button<{ isActive?: boolean }>`
  background-color: ${({ isActive }) => (isActive ? "#007bff" : "#f0f0f0")};
  color: ${({ isActive }) => (isActive ? "#fff" : "#333")};
  padding: 4% 30%;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  &:hover {
    background-color: ${({ isActive }) => (isActive ? "#0056b3" : "#ddd")};
  }
`;
