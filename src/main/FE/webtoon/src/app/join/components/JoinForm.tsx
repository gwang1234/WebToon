"use client";

import React, { useState } from "react";
import axios from "axios";
import { useRouter } from "next/navigation";
import InputField from "../components/InputField";
import SignupButton from "../components/SignupButton";
import styles from "../styles/styles";

const JoinForm: React.FC = () => {
  const [pw, setPw] = useState<string>(""); // 비밀번호 상태
  const [userName, setUserName] = useState<string>(""); // 유저네임 상태
  const [email, setEmail] = useState<string>(""); // 이메일 상태
  const [confirmPassword, setConfirmPassword] = useState<string>(""); // 비밀번호 확인 상태
  const [loading, setLoading] = useState<boolean>(false); // 로딩 상태
  const [error] = useState<string | null>(null); // 오류 상태
  const router = useRouter();

  // username 중복 확인
  const checkUsernameDuplicate = async (userName: string): Promise<boolean> => {
    try {
      const response = await axios.post(
        `${process.env.NEXT_PUBLIC_API_URL}/auth-username`,
        null, // 데이터 바디는 사용하지 않음
        {
          params: { userName }, // 쿼리 파라미터로 userName 전달
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      return response.data === true;
    } catch (error: unknown) {
      if (axios.isAxiosError(error)) {
        // axios 에러일 경우 처리
        console.error("유저네임 중복 확인 중 오류 발생:", error.response?.data);
        alert("유저네임 중복 확인 중 오류가 발생했습니다.");
      } else {
        // 예상치 못한 에러 처리
        console.error("예상치 못한 오류 발생:", error);
        alert("알 수 없는 오류가 발생했습니다.");
      }
      return false;
    }
  };

  // email 중복 확인
  const checkEmailDuplicate = async (email: string): Promise<boolean> => {
    try {
      const response = await axios.post(
        `${process.env.NEXT_PUBLIC_API_URL}/auth-email`,
        null, // 데이터 바디는 사용하지 않음
        {
          params: { email }, // 쿼리 파라미터로 email 전달
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      return response.data === true;
    } catch (error: unknown) {
      if (axios.isAxiosError(error)) {
        // axios 에러일 경우 처리
        console.error("이메일 중복 확인 중 오류 발생:", error.response?.data);
        alert("이메일 중복 확인 중 오류가 발생했습니다.");
      } else {
        // 예상치 못한 에러 처리
        console.error("예상치 못한 오류 발생:", error);
        alert("알 수 없는 오류가 발생했습니다.");
      }
      return false;
    }
  };

  // 회원가입 처리
  const onSubmit = async () => {
    if (pw !== confirmPassword) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }

    setLoading(true);

    try {
      // 유저네임 중복 확인
      const isUsernameValid = await checkUsernameDuplicate(userName);
      if (!isUsernameValid) {
        alert("이미 사용 중인 유저네임입니다.");
        setLoading(false);
        return;
      }

      // 이메일 중복 확인
      const isEmailValid = await checkEmailDuplicate(email);
      if (!isEmailValid) {
        alert("이미 사용 중인 이메일입니다.");
        setLoading(false);
        return;
      }

      // 회원가입 요청
      const response = await axios.post(
        `${process.env.NEXT_PUBLIC_API_URL}/jwt-auth`,
        {
          userName: userName,
          email,
          password: pw,
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
        alert("회원가입 성공!");
        router.push("/");
      }
    } catch (error: unknown) {
      if (axios.isAxiosError(error)) {
        console.error("회원가입 실패:", error.response?.data);
        alert(
          `회원가입 중 오류가 발생했습니다: ${
            error.response?.data?.message || "알 수 없는 오류"
          }`
        );
      } else {
        console.error("예상치 못한 오류 발생:", error);
        alert("회원가입 중 알 수 없는 오류가 발생했습니다.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <styles.Container>
        <styles.Main>
          {error && <p>{error}</p>}

          <InputField
            type="text"
            name="userName"
            placeholder="닉네임"
            value={userName}
            onChange={(e) => setUserName(e.target.value)}
            disabled={loading}
          />
          <InputField
            type="email"
            name="email"
            placeholder="이메일"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            disabled={loading}
          />
          <InputField
            type="password"
            name="pw"
            placeholder="비밀번호"
            value={pw}
            onChange={(e) => setPw(e.target.value)}
            disabled={loading}
          />
          <InputField
            type="password"
            name="confirmPassword"
            placeholder="비밀번호 확인"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            disabled={loading}
          />
          <SignupButton onClick={onSubmit} loading={loading} />

          <styles.JoinOrForget>
            <styles.Join href="/login">
              이미 계정이 있으신가요? 로그인
            </styles.Join>
          </styles.JoinOrForget>
        </styles.Main>
      </styles.Container>
    </>
  );
};

export default JoinForm;
