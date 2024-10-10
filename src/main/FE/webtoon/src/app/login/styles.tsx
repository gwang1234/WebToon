// styles.js
import styled from "styled-components";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  position: relative;
  width: 99vw;
  height: auto;
  margin-top: 8vh;
  background: #ffffff;
  justify-content: center;
  align-items: center;
`;

const Main = styled.main`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
`;

const LoginBox = styled.div`
  position: flex;
  flex-direction: column;
  top: 60%; /* 화면 높이의 30% */
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
`;

const InputField = styled.input`
  width: 30vw; /* 반응형 너비 */
  height: 6vh;
  margin-bottom: 2vh;
  padding: 1.5vh;
  background: #ffffff;
  border: 0.2vw solid #959595;
  border-radius: 2vw;
  font-size: 1.8vw;
`;

const LoginButton = styled.button`
  width: 30vw;
  height: 7vh;
  background: #a9dfbf;
  border-radius: 2vw;
  font-family: "Inter";
  font-style: normal;
  font-weight: 800;
  font-size: 2vw;
  line-height: 3vh;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #2c3e50;
  cursor: pointer;
`;

const ForgotPassword = styled.span`
  margin-top: 1vh; /* 간격을 줄여서 요소들을 더 가까이 붙이기 */
  font-size: 1vw;
  color: #928f8f;
  cursor: pointer;
  margin-left: 13vw;
`;

const JoinOrForget = styled.span`
  margin-top: 1vh; /* 간격을 줄여서 요소들을 더 가까이 붙이기 */
  font-size: 1vw;
  cursor: pointer;
`;

const Join = styled.a`
  margin-right: 10vw;
  color: #000000;
`;

const Forget = styled.a`
  margin-left: 50wv;
  color: #928f8f;
`;

const Or = styled.p`
  display: flex;
  align-items: center;
  margin: 4vh;
  font-size: 2vw;
  font-weight: 800;
  color: #000000;
  &::before,
  &::after {
    content: "";
    border-bottom: 1px solid #000000;
    flex: 1;
  }
`;

const SocialIcon = styled.img`
  margin-right: 1vw;
  width: 3vw;
  height: auto;
`;

const SLogin = styled.button`
  background: transparent;
  border: none;
  cursor: pointer;
  margin-right: 2vw;
`;

const components = {
  Container,
  Main,
  LoginBox,
  InputField,
  LoginButton,
  ForgotPassword,
  JoinOrForget,
  Join,
  Forget,
  Or,
  SocialIcon,
  SLogin,
};

export default components;
