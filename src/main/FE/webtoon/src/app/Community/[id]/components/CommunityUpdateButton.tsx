"use client";

import axios from "axios";
import { useState } from "react";

interface CommunityUpdateButtonProps {
  id: string; // 커뮤니티 ID
  title: string;
  content: string;
}

export default function CommunityUpdateButton({
  id,
  title,
  content,
}: CommunityUpdateButtonProps) {
  const [newTitle, setNewTitle] = useState<string>(title); // 제목 상태
  const [newContent, setNewContent] = useState<string>(content); // 내용 상태
  const [error, setError] = useState<string | null>(null); // 에러 상태
  const [success, setSuccess] = useState<boolean>(false); // 성공 여부 상태

  const handleUpdate = async () => {
    try {
      const token = sessionStorage.getItem("token"); // 세션에서 토큰 가져오기
      const provider_id = sessionStorage.getItem("provider_id");

      if (!token && !provider_id) {
        setError("로그인이 필요합니다.");
        return;
      }

      // token이 있을 경우 Authorization 헤더 추가
      const headers = token ? { Authorization: `Bearer ${token}` } : {};

      // 커뮤니티 게시글 수정 API 호출
      const response = await axios.patch(
        `${process.env.NEXT_PUBLIC_API_URL}/community/update/${id}`,
        {
          title: newTitle,
          content: newContent,
          provider_id: provider_id,
        },
        {
          headers,
        }
      );

      if (response.status === 200) {
        setSuccess(true); // 수정 성공 상태 설정
        setError(null); // 에러 초기화
      }
    } catch (error) {
      console.error("수정 중 오류가 발생했습니다:", error);
      setError("수정 중 오류가 발생했습니다.");
    }
  };

  return (
    <div>
      <input
        type="text"
        value={newTitle}
        onChange={(e) => setNewTitle(e.target.value)}
        placeholder="제목 수정"
      />
      <textarea
        value={newContent}
        onChange={(e) => setNewContent(e.target.value)}
        placeholder="내용 수정"
      />
      <button onClick={handleUpdate}>수정하기</button>
      {error && <p style={{ color: "red" }}>{error}</p>}
      {success && <p style={{ color: "green" }}>수정 성공!</p>}
    </div>
  );
}
