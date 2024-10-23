"use client"; // 클라이언트 컴포넌트로 설정

import { useEffect } from "react";
import axios from "axios";

interface TokenManagerProps {
  token: string;
  refreshToken: string;
}

export const TokenManager: React.FC<TokenManagerProps> = ({
  token,
  refreshToken,
}) => {
  useEffect(() => {
    // console.log("TokenManager 실행됨: ", { token, refreshToken });

    sessionStorage.setItem("token", token);
    sessionStorage.setItem("refreshToken", refreshToken || "");

    const decodedToken = decodeJwt(token);
    // console.log("JWT에서 추출한 사용자 정보:", decodedToken);

    sessionStorage.setItem("email", decodedToken.email || ""); // email 저장
    sessionStorage.setItem("provider_id", decodedToken.provider_id || ""); // provider_id 저장

    fetchUserName();

    const event = new Event("sessionUpdated");
    // console.log("sessionUpdated 이벤트 발생됨");
    window.dispatchEvent(event); // 이벤트 발생
  }, [token, refreshToken]);

  const decodeJwt = (token: string) => {
    try {
      const base64Url = token.split(".")[1];
      const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split("")
          .map(function (c) {
            return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
          })
          .join("")
      );

      // console.log("디코딩된 JWT 페이로드:", jsonPayload);
      return JSON.parse(jsonPayload);
    } catch (error) {
      console.error("JWT 디코딩 중 오류 발생:", error);
      return {}; // 디코딩 실패 시 빈 객체 반환
    }
  };

  // 사용자 이름을 가져오는 API 호출 함수 (POST 요청, JSON 전송)
  const fetchUserName = async () => {
    try {
      const token = sessionStorage.getItem("token");
      const email = sessionStorage.getItem("email") || "";
      const providerId = sessionStorage.getItem("provider_id") || null;

      // console.log("사용 중인 provider_id:", providerId);
      // console.log("사용 중인 email:", email);
      // console.log("사용 중인 token:", token);

      // JSON 형식으로 데이터 전송
      const response = await axios.post(
        `${process.env.NEXT_PUBLIC_API_URL}/users`,
        {
          provider_id: providerId,
          email: email,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      const { userName } = response.data;
      // console.log("서버에서 받아온 userName:", userName);

      sessionStorage.setItem("userName", userName || ""); // userName을 세션에 저장
      // console.log("저장된 userName:", sessionStorage.getItem("userName")); // 저장 확인
    } catch (error) {
      console.error("userName을 가져오는 중 오류 발생:", error);
    }
  };

  return null; // 렌더링할 UI가 없으므로 null 반환
};
