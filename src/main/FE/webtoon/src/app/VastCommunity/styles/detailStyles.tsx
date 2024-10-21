// styles/detailStyles.ts
import styled from "styled-components";

export const Container = styled.div`
  padding: 1%;
  max-width: 1000px;
  margin: 0 auto;
`;

export const Title = styled.h1`
  font-size: 1.5vw;
  margin-bottom: 2%;
`;

export const NameAndDate = styled.div`
  display: grid;
  grid-template-columns: 2fr 6fr 1fr 1fr;
  margin-bottom: 2%;
  border-bottom: 1px solid #9999997f;
`;

export const Name = styled.p`
  font-size: 0.8vw;
  margin-bottom: 20px;
  margin-right: 1%;

  p {
    overflow: hidden; /* 내용 넘침을 숨김 */
    text-overflow: ellipsis; /* 텍스트가 넘칠 때 ...로 표시 */
    white-space: nowrap; /* 텍스트가 한 줄로 표시 */
    max-width: 100%; /* 최대 너비 */
  }
`;

export const Date = styled.p`
  font-size: 0.8vw;
  margin-bottom: 20px;
  margin: 0% 1%;

  p {
    overflow: hidden; /* 내용 넘침을 숨김 */
    text-overflow: ellipsis; /* 텍스트가 넘칠 때 ...로 표시 */
    white-space: nowrap; /* 텍스트가 한 줄로 표시 */
    max-width: 100%; /* 최대 너비 */
  }
`;

export const Views = styled.p`
  font-size: 0.8vw;
  margin-bottom: 20px;
  margin-left: auto;
  text-align: right;
`;

export const Content = styled.p`
  font-size: 0.8vw;
  margin-bottom: 20px;
  margin-right: 3%;
`;

export const Good = styled.div`
  position: relative;
  transform: translateX(-50%);
  margin: 20% 0% 0% 50%;
  width: 9vw;
  height: 9vh;
`;

export const Details = styled.div`
  font-size: 0.9rem;
  color: #777;
  display: flex;
  justify-content: space-between;
`;
