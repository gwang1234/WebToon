// styles/mainStyles.ts
import styled from "styled-components";

export const Container = styled.div`
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
`;

export const WriteButtonContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
`;

export const WriteButton = styled.button`
  background-color: #0070f3;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;

  &:hover {
    background-color: #005bb5;
  }
`;

export const CommunityList = styled.ul`
  list-style: none;
  padding: 0;
  margin: 0;
`;

export const CommunityItem = styled.li`
  display: grid;
  grid-template-columns: 2fr 6fr 2fr 2fr;
  background-color: #ffffff;
  margin-bottom: 10px;
  padding: 15px;
  border-bottom: 2px solid #9999997f;

  /* 텍스트가 길 경우 처리 */
  h3,
  p {
    overflow: hidden; /* 내용 넘침을 숨김 */
    text-overflow: ellipsis; /* 텍스트가 넘칠 때 ...로 표시 */
    white-space: nowrap; /* 텍스트가 한 줄로 표시 */
    max-width: 100%; /* 최대 너비 */
  }
`;
export const CommunityName = styled.li`
  display: grid;
  grid-template-columns: 2fr 6fr 2fr 2fr;
  background-color: #ffffff;
  margin-bottom: 10px;
  padding: 15px;
  border-top: 2px solid black;
  border-bottom: 2px solid black;
`;

export const Pagination = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 20px;
`;

export const PageButton = styled.button<{ isActive: boolean }>`
  background-color: ${({ isActive }) => (isActive ? "#0070f3" : "#eaeaea")};
  color: ${({ isActive }) => (isActive ? "#fff" : "#000")};
  border: none;
  padding: 10px 15px;
  margin: 0 5px;
  border-radius: 5px;
  cursor: pointer;
  &:hover {
    background-color: ${({ isActive }) => (isActive ? "#005bb5" : "#ddd")};
  }
`;

export const LoadingMessage = styled.div`
  text-align: center;
  font-size: 18px;
  color: #333;
  padding: 20px;
`;

export const ErrorMessage = styled.div`
  text-align: center;
  font-size: 18px;
  color: red;
  padding: 20px;
`;

export const EmptyMessage = styled.div`
  text-align: center;
  font-size: 18px;
  color: #999;
  margin-top: 20px;
`;

// 검색 컴포넌트 스타일
export const SearchContainer = styled.div`
  display: flex;
  align-items: center;
  margin-left: 50%;
  margin-top: 5%;
  transform: translate(-50%, -50%);
  width: 80%;
`;

export const Dropdown = styled.select`
  padding: 10px;
  margin-right: 10px;
  border-radius: 5px;
  width: 10%;
  border: 1px solid #ccc;
`;

export const SearchInput = styled.input`
  padding: 10px;
  width: 80%;
  border-radius: 5px;
  border: 1px solid #ccc;
  margin-right: 10px;
`;

export const SearchButton = styled.button`
  padding: 10px 20px;
  background-color: #0070f3;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;

  &:hover {
    background-color: #005bb5;
  }
`;
