"use client";

import Head from "next/head";
import Image from "next/image";
import * as styles from "./styles"; // styled-components 스타일 임포트
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { useEffect, useState } from "react";
import axios from "axios";

// Webtoon 타입 정의
type Webtoon = {
  id: number;
  title: string;
  imageUrl: string;
};

// Mypage 타입 정의
type Mypage = {
  email: string;
  userName: string;
  password?: string;
};

const Main: React.FC = () => {
  const [mypage, setMypage] = useState<Mypage | null>(null); // 사용자 정보 저장 상태를 단일 객체로 설정
  const [webtoons, setWebtoons] = useState<Webtoon[]>([]); // 사용자 저장 그림 상태를 배열로 설정
  const [loading, setLoading] = useState<boolean>(false); // 로딩 상태 저장
  const [error, setError] = useState<string | null>(null); // 에러 상태 저장
  const [userName, setUserName] = useState<string>(""); // 닉네임 상태
  const [password, setPassword] = useState<string>(""); // 비밀번호 상태

  useEffect(() => {
    const fetchMypageData = async () => {
      setLoading(true); // 로딩 시작
      try {
        const token = sessionStorage.getItem("token");
        const providerId = sessionStorage.getItem("provider_id") || null;
        const email = sessionStorage.getItem("email") || null;

        // 사용자 정보 가져오기
        const response = await axios.post(
          `${process.env.NEXT_PUBLIC_API_URL}/users`,
          {
            provider_id: providerId,
            email: email,
          },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        setMypage(response.data); // 사용자 정보를 상태로 설정
        setUserName(response.data.userName); // 닉네임 상태 설정
      } catch (error) {
        setError("회원 정보를 불러오는 중 오류가 발생했습니다.");
      } finally {
        setLoading(false); // 로딩 상태 해제
      }

      try {
        const token = sessionStorage.getItem("token");
        const providerId = sessionStorage.getItem("provider_id") || null;

        // POST 요청을 사용하여 관심 웹툰 가져오기
        const response = await axios.post(
          `${process.env.NEXT_PUBLIC_API_URL}/webtoons/like-user`,
          {
            provider_id: providerId, // 필요에 따라 추가되는 데이터
          },
          {
            headers: {
              Authorization: `Bearer ${token}`, // 토큰을 헤더에 포함
            },
          }
        );

        setWebtoons(response.data); // 사용자 관심 웹툰 정보를 배열로 설정
      } catch (error) {
        setError("관심 웹툰 정보를 불러오는 중 오류가 발생했습니다.");
      } finally {
        setLoading(false); // 로딩 상태 해제
      }
    };

    fetchMypageData(); // API 호출
  }, []);

  const handleCorrection = async () => {
    try {
      const token = sessionStorage.getItem("token");

      await axios.put(
        `${process.env.NEXT_PUBLIC_API_URL}/users`,
        {
          userName: userName,
          password: password,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      alert("회원 정보가 수정되었습니다.");
    } catch (error) {
      alert("회원 정보 수정 중 오류가 발생했습니다.");
    }
  };

  // 웹툰의 개수에 따라 slidesToShow를 동적으로 설정
  const sliderSettings = {
    dots: true, // 하단 점 네비게이션 추가
    infinite: true, // 무한 슬라이드
    speed: 500, // 슬라이드 전환 속도
    slidesToShow: Math.min(webtoons.length, 3), // 보여줄 슬라이드 개수를 웹툰 개수로 제한
    slidesToScroll: 3, // 한 번에 넘길 슬라이드 수
  };

  return (
    <>
      <Head>
        <title>마이페이지</title>
      </Head>

      <styles.Container>
        {loading && <p>로딩 중...</p>} {/* 로딩 중일 때 표시 */}
        {error && <p>{error}</p>} {/* 에러 발생 시 표시 */}
        {!loading && !error && mypage && (
          <styles.Mypagebox>
            <styles.Title>마이페이지</styles.Title>
            <styles.NameAndId>
              <styles.Name>
                <styles.NameText>닉네임</styles.NameText>
                <styles.NameInput
                  value={userName}
                  onChange={(e) => setUserName(e.target.value)}
                />
              </styles.Name>
              <styles.Id>
                <styles.IdText>비밀번호</styles.IdText>
                <styles.IdInput
                  type="password"
                  placeholder="******"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </styles.Id>
            </styles.NameAndId>
            <styles.Email>
              <styles.EmailText>이메일</styles.EmailText>
              <styles.EmailInput value={mypage?.email || ""} readOnly />
            </styles.Email>
            <styles.CorrectionButton onClick={handleCorrection}>
              수정하기
            </styles.CorrectionButton>
            <styles.Webtoon>관심 작품</styles.Webtoon>
            {/* Slider, 웹툰이 있을 경우에만 렌더링 */}
            {webtoons.length > 0 ? (
              <styles.SlideshowContainer>
                <styles.Slid {...sliderSettings}>
                  {webtoons.map((webtoon) => (
                    <div key={webtoon.id}>
                      <Image
                        src={webtoon.imageUrl}
                        alt={webtoon.title}
                        width={800}
                        height={300}
                        style={{ width: "auto", height: "20vh" }} // 비율 유지를 위한 CSS 추가
                        priority // LCP 이미지에 priority 속성 추가
                      />
                    </div>
                  ))}
                </styles.Slid>
              </styles.SlideshowContainer>
            ) : (
              <p>관심 작품이 없습니다.</p> // 웹툰이 없을 때 표시할 메시지
            )}
          </styles.Mypagebox>
        )}
      </styles.Container>
    </>
  );
};

export default Main;
