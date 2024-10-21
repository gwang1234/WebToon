"use client";

import axios from "axios";
import Head from "next/head";
import Image from "next/image";
import styles from "./styles";
import { useState } from "react";
import { useRouter } from "next/navigation";
import { TokenManager } from "./components/TokenManager"; // TokenManager 불러오기

const Home: React.FC = () => {
  const [id, setId] = useState("");
  const [pw, setPw] = useState("");
  const [token, setToken] = useState<string | null>(null); // 토큰 저장
  const [refreshToken, setRefreshToken] = useState<string | null>(null); // 리프레시 토큰 저장
  const router = useRouter();

  // 로그인 정보 입력 핸들링
  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    if (name === "id") setId(value);
    if (name === "pw") setPw(value);
  };

  // 로그인 요청 처리 및 성공 시 토큰 저장
  // Home 컴포넌트
  const onSubmit = async () => {
    try {
      const response = await axios.post(
        `${process.env.NEXT_PUBLIC_API_URL}/jwt-login`,
        { email: id, password: pw },
        { withCredentials: true }
      );

      if (response.status === 200) {
        const token = response.headers["authorization"]?.split(" ")[1];
        const refreshToken = response.headers["refresh-token"] || ""; // refreshToken 없을 경우 빈 문자열 처리

        setToken(token);
        setRefreshToken(refreshToken);

        // console.log("로그인 성공, JWT 토큰:", token);
        // console.log("리프레시 토큰:", refreshToken);

        router.push("/"); // 로그인 성공 시 리다이렉트
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

  // 구글 로그인 요청
  const handleGoogleLogin = () => {
    window.location.href = `${process.env.NEXT_PUBLIC_API_URL_Login}/oauth2/authorization/google`;
  };

  // 카카오 로그인 요청
  const handleKakaoLogin = () => {
    window.location.href = `${process.env.NEXT_PUBLIC_API_URL_Login}/oauth2/authorization/kakao`;
  };

  return (
    <div>
      <Head>
        <title>웹툰 에픽스 - 로그인</title>
      </Head>

      {/* TokenManager 컴포넌트를 통해 토큰을 세션에 저장 */}
      {token && (
        <TokenManager token={token} refreshToken={refreshToken || ""} />
      )}

      <styles.Container>
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

          {/* 구글 로그인 */}
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

          {/* 카카오 로그인 */}
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
      </styles.Container>
    </div>
  );
};

export default Home;
