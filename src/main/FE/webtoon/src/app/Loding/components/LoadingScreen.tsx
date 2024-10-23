// components/LoadingScreen.tsx
import React from "react";
import * as styles from "../styles/styles"; // 스타일 가져오기

const LoadingScreen: React.FC = () => {
  return (
    <styles.Container>
      <p>로딩 중...</p>
    </styles.Container>
  );
};

export default LoadingScreen;
