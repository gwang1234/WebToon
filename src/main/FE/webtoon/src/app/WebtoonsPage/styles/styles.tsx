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

  &:hover {
    transform: translateY(-5px);
  }

  img {
    border-radius: 8px;
    width: 100%;
    height: auto;
  }
`;

// 페이지네이션 컨테이너
export const PaginationContainer = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 1%;
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
