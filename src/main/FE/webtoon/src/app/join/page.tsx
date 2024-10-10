"use client"; // 클라이언트 컴포넌트로 지정

import React from "react";
import Head from "next/head";
import styles from "./styles/styles";
import JoinForm from "./components/JoinForm";

const Join: React.FC = () => {
  return (
    <>
      <Head>
        <title>회원가입</title>
      </Head>

      <styles.Container>
        <styles.Main>
          <JoinForm />
        </styles.Main>
      </styles.Container>
    </>
  );
};

export default Join;
