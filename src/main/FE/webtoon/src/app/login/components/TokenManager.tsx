// components/TokenManager.tsx
import { useEffect } from "react";

interface TokenManagerProps {
  token: string;
  refreshToken: string;
}

export const TokenManager: React.FC<TokenManagerProps> = ({
  token,
  refreshToken,
}) => {
  useEffect(() => {
    // 세션 스토리지에 토큰과 리프레시 토큰을 저장
    console.log("TokenManager 실행됨: ", { token, refreshToken });

    sessionStorage.setItem("token", token);
    sessionStorage.setItem("refreshToken", refreshToken || "");

    // JWT에서 사용자 정보(이메일, provider_id)를 추출하고 세션에 저장
    const decodedToken = decodeJwt(token);
    console.log("JWT에서 추출한 사용자 정보:", decodedToken);

    sessionStorage.setItem("username", decodedToken.user || ""); // 예시로 username을 저장
    sessionStorage.setItem("email", decodedToken.email || ""); // email 저장
    sessionStorage.setItem("provider_id", decodedToken.provider_id || ""); // provider_id 저장

    // 세션 정보가 저장되었음을 알리기 위해 커스텀 이벤트 발생
    const event = new Event("sessionUpdated");
    window.dispatchEvent(event);

    // 저장된 값들 확인용 콘솔 출력
    console.log("저장된 username:", sessionStorage.getItem("username"));
    console.log("저장된 email:", sessionStorage.getItem("email"));
    console.log("저장된 provider_id:", sessionStorage.getItem("provider_id"));
  }, [token, refreshToken]);

  // JWT 디코딩 함수 (토큰에서 페이로드 추출)
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

      console.log("디코딩된 JWT 페이로드:", jsonPayload); // 디코딩된 페이로드 출력
      return JSON.parse(jsonPayload); // 페이로드를 파싱하여 반환
    } catch (error) {
      console.error("JWT 디코딩 중 오류 발생:", error);
      return {}; // 디코딩 실패 시 빈 객체 반환
    }
  };

  return null; // 렌더링할 UI가 없으므로 null 반환
};
