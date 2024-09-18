"use client";

import axios from "axios";
import Head from "next/head";
import { useState } from "react";
import Header from "../CommonLayout/Header/page";
import styles from "./styles";

const Join: React.FC = () => {
  const [id, setId] = useState("");
  const [pw, setPw] = useState("");
  const [userName, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    switch (name) {
      case "id":
        setId(value);
        break;
      case "pw":
        setPw(value);
        break;
      case "userName":
        setUserName(value);
        break;
      case "email":
        setEmail(value);
        break;
      case "confirmPassword":
        setConfirmPassword(value);
        break;
    }
  };

  const onSubmit = async () => {
    if (pw !== confirmPassword) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }

    try {
      const response = await axios.post(
        `${process.env.NEXT_PUBLIC_API_URL}/signup`,
        {
          id,
          userName,
          email,
          password: pw,
        }
      );

      if (response.status === 200) {
        alert("회원가입 성공!");
        window.location.href = "/login";
      }
    } catch (error) {
      console.error("회원가입 실패:", error);
      if (axios.isAxiosError(error)) {
        alert(
          error.response?.data?.message || "회원가입 중 오류가 발생했습니다."
        );
      }
    }
  };

  return (
    <>
      <Head>
        <title>회원가입</title>
      </Head>

      <Header />

      <styles.Container>
        <styles.Main>
          <styles.InputField
            type="text"
            name="id"
            placeholder="아이디"
            onChange={onChange}
            value={id}
          />
          <styles.InputField
            type="text"
            name="userName"
            placeholder="이름"
            onChange={onChange}
            value={userName}
          />
          <styles.InputField
            type="email"
            name="email"
            placeholder="이메일"
            onChange={onChange}
            value={email}
          />
          <styles.InputField
            type="password"
            name="pw"
            placeholder="비밀번호"
            onChange={onChange}
            value={pw}
          />
          <styles.InputField
            type="password"
            name="confirmPassword"
            placeholder="비밀번호 확인"
            onChange={onChange}
            value={confirmPassword}
          />
          <styles.SignupButton onClick={onSubmit}>회원가입</styles.SignupButton>

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

export default Join;
