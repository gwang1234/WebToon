// 글 작성 및 삭제 기능이 포함된 페이지 예시

"use client";

import { useState, useEffect } from "react";
import axios from "axios";
import { useRouter } from "next/navigation"; // useRouter 가져오기
import * as styles from "../styles/updatePage";

export default function CommunityWritePage() {
  const [title, setTitle] = useState<string>(""); // 제목 상태
  const [content, setContent] = useState<string>(""); // 내용 상태
  const [, setProviderId] = useState<string | null>(null); // provider_id 상태
  const [, setEmail] = useState<string | null>(null); // email 상태
  const [error, setError] = useState<string | null>(null); // 에러 상태
  const [success, setSuccess] = useState<boolean>(false); // 성공 여부 상태
  const [postId, setPostId] = useState<string | null>(null); // 생성된 글 ID 저장

  const router = useRouter(); // useRouter 가져오기

  // 페이지 로드 시 세션에서 provider_id와 email, token 가져오기
  useEffect(() => {
    const storedProviderId = sessionStorage.getItem("provider_id");
    const storedEmail = sessionStorage.getItem("email");
    const token = sessionStorage.getItem("token"); // 토큰 가져오기

    if (storedProviderId) {
      setProviderId(storedProviderId);
    }

    if (storedEmail) {
      setEmail(storedEmail);
    }

    // 세션 정보를 콘솔에 출력
    // console.log("세션에 저장된 provider_id:", storedProviderId);
    // console.log("세션에 저장된 email:", storedEmail);
    // console.log("세션에 저장된 토큰:", token); // 토큰 확인
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const token = sessionStorage.getItem("token");

      const response = await axios.post(
        `${process.env.NEXT_PUBLIC_API_URL}/community/write`,
        {
          id: null, // id는 보통 null로 전송
          title, // 제목
          content, // 내용
        },
        {
          headers: {
            Authorization: `Bearer ${token}`, // 토큰 포함
          },
        }
      );

      if (response.status === 201) {
        // console.log("글 작성 성공");
        setPostId(response.data.id); // 생성된 게시글의 ID 저장
        setSuccess(true); // 성공 시 success 상태 업데이트
      }
    } catch (error) {
      console.error("글 작성 중 오류가 발생했습니다:", error);
      setError("글 작성 중 오류가 발생했습니다.");
    }
  };

  const handleDelete = async () => {
    try {
      const token = sessionStorage.getItem("token");

      if (!token) {
        setError("로그인이 필요합니다.");
        return;
      }

      if (!postId) {
        setError("삭제할 게시글이 없습니다.");
        return;
      }

      // 게시글 삭제 API 호출
      const response = await axios.delete(
        `${process.env.NEXT_PUBLIC_API_URL}/community/delete/${postId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`, // 토큰을 헤더에 포함
          },
        }
      );

      if (response.status === 200) {
        // console.log("글 삭제 성공");
        router.push("/Community"); // 삭제 성공 후 커뮤니티 목록 페이지로 이동
      }
    } catch (error) {
      console.error("글 삭제 중 오류가 발생했습니다:", error);
      setError("글 삭제 중 오류가 발생했습니다.");
    }
  };

  // 성공 후 리다이렉션
  useEffect(() => {
    if (success) {
      router.push("/Community"); // 성공 시 커뮤니티 목록 페이지로 이동
    }
  }, [success, router]);

  // 입력 시 높이 자동 조절 함수
  const handleInputChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    e.target.style.height = "auto"; // 높이를 자동으로 초기화
    e.target.style.height = `${e.target.scrollHeight}px`; // 내용에 맞춰 높이 조절
    setContent(e.target.value);
  };

  // 입력 시 높이 자동 조절 함수
  const handleNameChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    e.target.style.height = "auto"; // 높이를 자동으로 초기화
    e.target.style.height = `${e.target.scrollHeight}px`; // 내용에 맞춰 높이 조절
    setTitle(e.target.value);
  };

  return (
    <styles.FormContainer>
      <form onSubmit={handleSubmit}>
        <styles.FormTitleSection>
          <styles.TitleInput
            value={title}
            onChange={(e) => {
              setTitle(e.target.value);
              handleNameChange(e);
            }}
            required
            placeholder="제목을 입력해주세요"
          />
        </styles.FormTitleSection>
        <styles.ContentSection>
          <styles.ContentInput
            value={content}
            onChange={(e) => {
              setContent(e.target.value); // 첫 번째 함수 호출
              handleInputChange(e); // 두 번째 함수 호출
            }}
            required
            placeholder="내용을 입력해주세요"
          />
        </styles.ContentSection>
        <styles.SubmitButton type="submit">글 작성하기</styles.SubmitButton>
      </form>
      {postId && <button onClick={handleDelete}>삭제하기</button>}
      {error && <p style={{ color: "red" }}>{error}</p>}
    </styles.FormContainer>
  );
}
