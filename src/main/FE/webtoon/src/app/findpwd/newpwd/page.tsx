"use client";

import styled from "styled-components";

const Container = styled.div`
  position: relative;
  background: #ffffff;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const LogoImg = styled.img`
  width: 100px;
  height: 100px;
`

const Main = styled.div`
margin-top: 150px;
  display: flex;
  flex-direction: column;
  align-items: center;
  /* height: 100vh; */
`

const findpwd: React.FC = () => {

  return (
      <>
      <Container>
        <Main>
        <LogoImg src="/email.png"/>
        새 비밀번호를 이메일로 전송하였습니다!
        </Main>
      </Container>
      </>
    );
  };
  
export default findpwd;