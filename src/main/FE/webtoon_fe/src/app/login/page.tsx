"use client";

import axios from "axios";
import Head from "next/head";
import Image from "next/image";
import styles from "../styles/Home";
import Header from "../CommonLayout/Header/page";
import { useEffect, useState } from "react";

const Home: React.FC = () => {
  const [id, setId] = useState("");
  const [pw, setPw] = useState("");

  // 로그인 상태 유지용 토큰 확인
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      // 이미 로그인된 상태라면 대시보드로 리다이렉트
      window.location.href = "/";
    }
  }, []);

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const {
      target: { name, value },
    } = e;

    // 1. 정보 분류
    switch (name) {
      case "id":
        setId(value);
        break;
      case "pw":
        setPw(value);
        break;
    }
  };

  const onSubmit = async () => {
    try {
      // 로그인 요청을 서버로 보냄
      const response = await axios.post(
        `${process.env.REACT_APP_API_URL}/login`,
        {
          id,
          pw,
        }
      );

      // 서버로부터 받은 응답 처리
      if (response.status === 200) {
        console.log("로그인 성공:", response.data);
        // 예시: 로그인 성공 후 토큰 저장 및 대시보드로 리다이렉션
        localStorage.setItem("token", response.data.token);
        window.location.href = "/dashboard";
      }
    } catch (error) {
      console.error("로그인 실패:", error);
      if (axios.isAxiosError(error)) {
        alert(
          error.response?.data?.message || "로그인 중 오류가 발생했습니다."
        );
      }
    }
  };

  const handleGoogleLogin = () => {
    window.location.href = `${process.env.NEXT_PUBLIC_API_URL}/auth/google`; // 백엔드의 구글 로그인 엔드포인트로 이동
  };

  const handleKakaoLogin = () => {
    window.location.href = `${process.env.NEXT_PUBLIC_API_URL}/auth/kakao`; // 백엔드의 카카오 로그인 엔드포인트로 이동
  };

  return (
    <div>
      <Head>
        <title>웹툰 에픽스 - 로그인</title>
      </Head>

      <main>
        <Header />
        {/* 로그인 폼 */}
        <styles.LoginBox>
          <styles.InputField
            name="id"
            type="text"
            placeholder="아이디"
            onChange={onChange}
            value={id}
          />
          <styles.InputField
            name="pw"
            type="password"
            placeholder="비밀번호"
            onChange={onChange}
            value={pw}
          />
          <styles.LoginButton onClick={onSubmit}>로그인</styles.LoginButton>

          <styles.JoinOrForget>
            <styles.Join href="/join">회원가입</styles.Join>
            <styles.Forget href="#">앗, 비밀번호를 잊어버렸어요!</styles.Forget>
          </styles.JoinOrForget>

          <styles.Or>또는</styles.Or>

          <styles.SLogin onClick={handleGoogleLogin}>
            <Image
              src="/google-logo.svg"
              alt="Google"
              width={100}
              height={100}
              priority
              style={{ width: "100%", height: "auto", margin: "2vh" }}
            />
          </styles.SLogin>

          <styles.SLogin onClick={handleKakaoLogin}>
            <Image
              src="/kakao-logo.svg"
              alt="kakao"
              width={30}
              height={30}
              priority
              style={{ width: "100%", height: "auto", margin: "2vh" }}
            />
          </styles.SLogin>
        </styles.LoginBox>
      </main>
    </div>
  );
};

export default Home;
