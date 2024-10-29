"use client";

import styled from "styled-components";
import { useState } from "react";
import { useRouter } from "next/navigation";
import axios from "axios";

// 컨테이너: 화면 전체 크기를 차지하는 배경 스타일
const Container = styled.div`
  position: relative;
  width: 99vw;
  height: 76vh;
  background: #ffffff;
  display: flex;
  justify-content: center;
  align-items: center;
`;

// 메인 섹션: 중앙에 위치시키는 스타일 (배경 흰색)
const Main = styled.main`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 40vw;
  padding: 2vw;
  background-color: #ffffff;
  border-radius: 1vw;
  box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.05); /* 부드러운 그림자 */
  border: 1px solid #e0e0e0; /* 연한 회색 테두리 */
`;

// 입력 박스 스타일
const InputField = styled.input`
  width: 100%;
  height: 7vh;
  margin-bottom: 1.5vh; /* 여백을 약간 줄임 */
  padding: 1.2vh;
  background: #fafafa; /* 약간의 회색 배경 */
  border: 1px solid #d1d1d1;
  border-radius: 0.5vw;
  font-size: 1vw;
  outline: none;

  &:focus {
    border-color: #80bdff;
    box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
  }
`;

// 회원가입 버튼 스타일
const SignupButton = styled.button`
  width: 100%;
  height: 6vh;
  background: #63b7a0;
  border-radius: 0.5vw;
  font-family: "Inter", sans-serif;
  font-weight: bold;
  font-size: 1.2vw;
  color: #ffffff;
  cursor: pointer;
  border: none;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #4cae8c; /* 버튼에 호버 효과 */
  }
`;

// 하단 링크 (로그인으로 이동)
const JoinOrForget = styled.div`
  margin-top: 1vh;
  font-size: 0.9vw;
  color: #6c757d;
  text-align: center;
`;

const Join = styled.a`
  color: #007bff;
  text-decoration: none;

  &:hover {
    text-decoration: underline;
  }
`;




const Findpwd: React.FC = () => {
    const [userName, setUserName] = useState<string>(""); // 유저네임 상태
    const [email, setEmail] = useState<string>(""); // 이메일 상태
    const router = useRouter();

    const onSubmit = async () => {

        try {

          const response = await axios.post(
            `${process.env.NEXT_PUBLIC_API_URL}/user/new-pwd`,
            {
              userName: userName,
              email: email
            },
            {
              headers: {
                "Content-Type": "application/json", // JSON 형식으로 데이터 전송
              },
            }
          );
    
          if (response.status === 200) {
            const token = response.data.token;
            sessionStorage.setItem("token", token);
            router.push("/findpwd/newpwd");
          }
        } catch (error: unknown) {
          if (axios.isAxiosError(error)) {
            console.error("비밀번호 찾기 실패:", error.response?.data);
            alert(
              `비밀번호 찾는 중 오류가 발생했습니다: ${
                error.response?.data?.message || "알 수 없는 오류"
              }`
            );
          } else {
            console.error("예상치 못한 오류 발생:", error);
            alert("비밀번호 변경 중 알 수 없는 오류가 발생했습니다.");
          }
        } finally {
    
        }
      };

  return (
      <>
      <Container>
        <Main>
        <InputField
            type="text"
            name="userName"
            placeholder="닉네임"
            value={userName}
            onChange={(e) => setUserName(e.target.value)}
          />
          <InputField
            type="email"
            name="email"
            placeholder="이메일"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <SignupButton onClick={onSubmit}>비밀번호 찾기</SignupButton>
          <JoinOrForget>
            <Join href="/login">
              이미 계정이 있으신가요? 로그인
            </Join>
          </JoinOrForget>
        </Main>
      </Container>
      </>
    );
  };
  
export default Findpwd;