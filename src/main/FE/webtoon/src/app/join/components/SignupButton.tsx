"use client"; // 클라이언트 컴포넌트로 지정

import React from "react";
import styles from "../styles/styles";

type SignupButtonProps = {
  onClick: () => void;
  loading: boolean;
};

const SignupButton: React.FC<SignupButtonProps> = ({ onClick, loading }) => {
  return (
    <styles.SignupButton onClick={onClick} disabled={loading}>
      {loading ? "회원가입 중..." : "회원가입"}
    </styles.SignupButton>
  );
};

export default SignupButton;
