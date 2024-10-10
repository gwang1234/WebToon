import axios from "axios";
import { useState } from "react";

interface CommunityDeleteButtonProps {
  id: string; // 커뮤니티 ID
}

export default function CommunityDeleteButton({
  id,
}: CommunityDeleteButtonProps) {
  const [error, setError] = useState<string | null>(null); // 에러 상태
  const [success, setSuccess] = useState<boolean>(false); // 성공 여부 상태

  const handleDelete = async () => {
    try {
      const token = sessionStorage.getItem("token"); // 세션에서 토큰 가져오기
      let provider_id = sessionStorage.getItem("provider_id"); // provider_id 가져오기

      if (!token && !provider_id) {
        setError("로그인이 필요합니다.");
        return;
      }

      // provider_id가 null이면 빈 문자열로 설정
      provider_id = provider_id || "";

      console.log("Deleting community with id:", id);
      console.log("User token:", token);
      console.log("Provider ID:", provider_id);

      // 커뮤니티 게시글 삭제 API 호출
      const response = await axios.request({
        method: "delete",
        url: `${process.env.NEXT_PUBLIC_API_URL}/community/delete/${id}`,
        headers: {
          Authorization: `Bearer ${token}`, // 토큰을 헤더에 포함하여 전송
        },
        data: { provider_id }, // provider_id를 빈 문자열로 전송
      });

      if (response.status >= 200 && response.status < 300) {
        setSuccess(true); // 삭제 성공 상태 설정
        setError(null); // 에러 초기화
        console.log("삭제 성공!");
      } else {
        console.error("삭제 실패:", response);
        setError("삭제에 실패했습니다.");
      }
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error("서버 응답 코드:", error.response?.status);
        console.error("서버 응답 데이터:", error.response?.data); // 서버 응답 내용 출력
        setError(
          `서버 오류: ${error.response?.data?.message || "알 수 없는 오류"}`
        );
      } else {
        console.error("예상치 못한 오류가 발생했습니다:", error);
        setError("삭제 중 예상치 못한 오류가 발생했습니다.");
      }
    }
  };

  return (
    <div>
      <button onClick={handleDelete}>삭제하기</button>
      {error && <p style={{ color: "red" }}>{error}</p>}
      {success && <p style={{ color: "green" }}>삭제 성공!</p>}
    </div>
  );
}
