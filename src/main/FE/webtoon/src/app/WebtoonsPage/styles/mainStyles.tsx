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

// export const Button = styled.button<{ isActive: boolean }>`
//   padding: 0.5% 1%;
//   font-size: 20px;
//   background-color: transparent; /* 배경색 제거 */
//   color: ${(props) =>
//     props.isActive
//       ? "#0070f3"
//       : "#000"}; /* 활성화 상태에 따라 글자 색상 변경 */
//   border: none;
//   cursor: pointer;
//   text-decoration: ${(props) => (props.isActive ? "underline" : "none")};
//   text-decoration-color: ${(props) =>
//     props.isActive
//       ? "#0070f3"
//       : "transparent"}; /* 활성화된 경우 글자 밑줄 색상 */
//   text-underline-offset: 4px; /* 밑줄과 텍스트 사이의 간격 조정 */

//   &:hover {
//     text-decoration-color: #0070f3; /* 호버 시 밑줄 색상 */
//   }
// `;

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
