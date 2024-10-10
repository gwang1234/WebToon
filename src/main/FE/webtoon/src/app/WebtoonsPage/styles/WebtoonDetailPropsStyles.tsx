import styled from "styled-components";

export const Section = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #ffffff;
  min-height: 70vh;
  width: 100%;
  height: 100%;
`;

export const WebtoonDetailContainer = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr; /* 두 열을 동일하게 나눔 (각각 50%) */
  align-items: center;
  background-color: white;
  padding: 20px;
  max-width: 1000px;
  width: 100vw;
`;

export const Images = styled.div`
  display: flex;
  width: 60vw;
  height: 50vh;
  max-width: 400px; // 최대 너비
  min-width: 200px; // 최소 너비
  max-height: 600px; // 최대 높이
  min-height: 300px; // 최소 높이
  margin-right: 10%;
  position: relative; // fill 속성을 사용하려면 부모에 relative 필요
  aspect-ratio: 2 / 3; // 2:3 비율 고정
  overflow: hidden; // 비율 밖의 내용이 잘리지 않도록 설정
`;

export const Information = styled.div`
  display: flex;
  flex-direction: column;
`;

export const WebtoonTitle = styled.p`
  font-size: 2vw;
  font-weight: bold;
  margin-bottom: 1%;
  color: #333;
`;

export const WebtoonAuthor = styled.p`
  font-size: 1vw;
  font-weight: bold;
  margin-bottom: 1%;
  color: #333;
`;

export const WebtoonViews = styled.p`
  font-size: 1vw;
  font-weight: bold;
  margin-bottom: 5%;
  color: #333;
`;

export const WebtoonDescription = styled.p`
  font-size: 1vw;
  font-weight: bold;
  margin-bottom: 5%;
  color: #333;
`;

export const Buttons = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr; /* 두 열을 동일하게 나눔 (각각 50%) */
  width: 100%;
  gap: 10%;
`;

export const GoToButton = styled.button`
  width: 100%;
  height: auto;
  font-size: 110%;
  color: #ffffff;
  padding: 11% 6%;

  background: #4baea0;
  border-radius: 50px;
  border: none;
  cursor: pointer;

  &:hover {
    background-color: #2e6d65;
  }
`;

export const Like = styled.button`
  width: 100%;
  height: auto;
  font-size: 1vw;
  color: #ffffff;
  padding: 3%;

  background: #4baea0;
  border-radius: 50px;
  border: none;
  cursor: pointer;

  &:hover {
    background-color: #2e6d65;
  }
`;

export const LoadingMessage = styled.p`
  font-size: 18px;
  color: #999;
  text-align: center;
`;

export const ErrorMessage = styled.p`
  font-size: 18px;
  color: red;
  text-align: center;
`;
