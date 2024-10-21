"use client"; // 클라이언트 컴포넌트 선언

import { useState, useEffect } from "react";
import { useRouter, useParams } from "next/navigation";
import axios from "axios"; // axios 임포트
import * as styles from "../../styles/updatePage";

const UpdateCommunityPost = () => {
  const router = useRouter();
  const { id } = useParams(); // URL에서 id를 가져옴

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [loading, setLoading] = useState(true); // 로딩 상태 추가
  const [error, setError] = useState(""); // 에러 상태 추가

  // 게시글 정보를 가져오는 로직
  useEffect(() => {
    const numericId = Number(id); // id를 number로 변환
    if (numericId) {
      // console.log("가져온 ID:", numericId); // 가져온 id 콘솔에 출력
    }

    const fetchPost = async () => {
      if (numericId) {
        try {
          const response = await axios.get(
            `${process.env.NEXT_PUBLIC_API_URL}/community/detail/${numericId}`
          );
          // console.log("게시글 데이터:", response.data); // 가져온 게시글 데이터 콘솔에 출력
          setTitle(response.data.title || "");
          setContent(response.data.content || "");
        } catch (error) {
          console.error("게시글을 불러오는 중 오류:", error);
          setError("게시글을 불러오는 중 오류가 발생했습니다.");
        } finally {
          setLoading(false); // 로딩 완료
        }
      }
    };

    fetchPost();
  }, [id]);

  // 업데이트 요청을 보내는 함수
  const handleUpdate = async () => {
    if (!title || !content) {
      alert("제목과 내용을 모두 입력해주세요.");
      return;
    }

    const provider_id = sessionStorage.getItem("provider_id") || ""; // 세션에서 provider_id 가져오기
    const token = sessionStorage.getItem("token"); // 세션에서 token 가져오기
    const numericId = Number(id); // id를 number로 변환

    // 세션 값 확인
    // console.log("provider_id:", provider_id);
    // console.log("token:", token);

    if (!token) {
      alert("인증 토큰이 없습니다. 다시 로그인해 주세요.");
      return;
    }

    // 전송하는 데이터 확인
    // console.log("업데이트 데이터:", {
    //   title: title.trim(),
    //   content: content.trim(),
    //   provider_id,
    // });

    try {
      const response = await axios.patch(
        `${process.env.NEXT_PUBLIC_API_URL}/community/update/${numericId}`,
        {
          id: numericId, // 페이지 id도 함께 body로 전달
          title: title.trim(), // 개별 필드로 전달
          content: content.trim(), // 개별 필드로 전달
          provider_id: provider_id, // 개별 필드로 전달
        },
        {
          headers: {
            Authorization: `Bearer ${token}`, // 토큰 확인
          },
        }
      );

      if (response.status >= 200 && response.status < 300) {
        // console.log("수정 성공 응답 데이터:", response.data); // 수정 성공 데이터 출력
        alert("수정이 완료되었습니다!");
        router.push(`/Community/${numericId}`); // 수정 후 해당 게시글로 리다이렉트
      } else {
        console.error("수정 실패 응답 데이터:", response);
        alert(`수정에 실패했습니다: ${response.data.message}`);
      }
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error("서버 응답 코드:", error.response?.status);
        console.error("서버 응답 데이터:", error.response?.data); // 서버 응답 내용 출력
        alert(
          `수정에 실패했습니다: ${
            error.response?.data?.message || "알 수 없는 오류"
          }`
        );
      } else {
        console.error("예상치 못한 오류가 발생했습니다:", error);
        alert("서버 오류가 발생했습니다.");
      }
    }
  };

  if (loading) {
    return <p>로딩 중...</p>; // 로딩 중 표시
  }

  if (error) {
    return <p>{error}</p>; // 에러 메시지 표시
  }

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
      <h1>커뮤니티 글 수정</h1>
      <form onSubmit={(e) => e.preventDefault()}>
        <styles.FormTitleSection>
          <styles.TitleInput
            id="title"
            value={title}
            onChange={(e) => {
              setTitle(e.target.value);
              handleNameChange(e);
            }}
            placeholder="제목을 입력하세요"
          />
        </styles.FormTitleSection>
        <styles.ContentSection>
          <styles.ContentInput
            id="content"
            value={content}
            onChange={(e) => {
              setContent(e.target.value);
              handleInputChange(e);
            }}
            placeholder="내용을 입력하세요"
          />
        </styles.ContentSection>
        <styles.SubmitButton type="submit" onClick={handleUpdate}>
          수정하기
        </styles.SubmitButton>
      </form>
    </styles.FormContainer>
  );
};

export default UpdateCommunityPost;
