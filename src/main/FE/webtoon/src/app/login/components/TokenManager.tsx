"use client";

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
    // 토큰 및 리프레시 토큰을 세션에 저장
    sessionStorage.setItem("token", token);
    sessionStorage.setItem("refreshToken", refreshToken || "");

    const decodedToken = decodeJwt(token);

    // 일반 로그인일 경우 email을 세션에 저장
    sessionStorage.setItem("email", decodedToken.email || "");

    // 사용자 이름 가져오기
    fetchUserName();

    // 세션 업데이트 이벤트 발생
    const event = new Event("sessionUpdated");
    window.dispatchEvent(event);
  }, [token, refreshToken]);

  const decodeJwt = (token: string) => {
    try {
      const base64Url = token.split(".")[1];
      const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split("")
          .map((c) => "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2))
          .join("")
      );
      return JSON.parse(jsonPayload);
    } catch (error) {
      console.error("JWT 디코딩 중 오류 발생:", error);
      return {};
    }
  };

  const fetchUserName = async () => {
    try {
      const token = sessionStorage.getItem("token");
      const email = sessionStorage.getItem("email") || "";
      const provider_id = sessionStorage.getItem("provider_id");

      // token이 있을 경우 Authorization 헤더 추가
      const headers = token ? { Authorization: `Bearer ${token}` } : {};

      const response = await axios.post(
        `${process.env.NEXT_PUBLIC_API_URL}/users`,
        {
          provider_id: provider_id,
          email: email,
        },
        {
          headers,
        }
      );

      const { userName } = response.data;
      sessionStorage.setItem("userName", userName || "");
    } catch (error) {
      console.error("userName을 가져오는 중 오류 발생:", error);
    }
  };

  return null;
};
