import styled from "styled-components";

export const Container = styled.div`
  display: flex;
  flex-direction: column;
`;

export const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-around;
  width: 50%;
  margin-top: 1%;
  margin-bottom: -1%;
  margin-left: 25%;
`;

export const Button = styled.button<{ isActive: boolean }>`
  padding: 10px 20px;
  font-size: 16px;
  background-color: ${(props) => (props.isActive ? "#0070f3" : "#ccc")};
  color: ${(props) => (props.isActive ? "#fff" : "#000")};
  border: none;
  border-radius: 5px;
  cursor: pointer;

  &:hover {
    background-color: ${(props) => (props.isActive ? "#005bb5" : "#bbb")};
  }
`;

// 검색 컴포넌트 스타일
export const SearchContainer = styled.div`
  display: flex;
  align-items: center;
  margin-left: 50%;
  transform: translate(-50%, -50%);
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

export const SearchResultsContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 20px;
`;

export const WebtoonCard = styled.div`
  width: 150px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 10px;
  text-align: center;
  background-color: white;
`;

export const WebtoonTitle = styled.h3`
  font-size: 16px;
  margin-top: 10px;
`;
