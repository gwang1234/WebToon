"use client";

import Head from "next/head";
import Image from "next/image";
import * as styles from "./styles"; // styled-components 스타일 임포트
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

const Main: React.FC = () => {
  const settings = {
    dots: true, // 하단 점 네비게이션 추가
    infinite: true, // 무한 슬라이드
    speed: 500, // 슬라이드 전환 속도
    slidesToShow: 3, // 한 번에 보여줄 슬라이드 수
    slidesToScroll: 1, // 한 번에 넘길 슬라이드 수
  };

  return (
    <>
      <Head>
        <title>웹툰 별점 탑 10</title>
      </Head>

      <styles.Container>
        <styles.Mypagebox>
          <styles.Title>마이페이지</styles.Title>
          <styles.NameAndId>
            <styles.Name>
              <styles.NameText>닉네임</styles.NameText>
              <styles.NameInput />
            </styles.Name>
            <styles.Id>
              <styles.IdText>비밀번호</styles.IdText>
              <styles.IdInput />
            </styles.Id>
          </styles.NameAndId>
          <styles.Email>
            <styles.EmailText>이메일</styles.EmailText>
            <styles.EmailInput />
          </styles.Email>
          <styles.CorrectionButton>수정하기</styles.CorrectionButton>
          <styles.Webtoon>관심 작품</styles.Webtoon>
          {/* Slider */}
          <styles.SlideshowContainer>
            <styles.Slid {...settings}>
              <div>
                <Image
                  src="/eximg1.jpg"
                  alt="Example Image 1"
                  width={800}
                  height={300}
                  style={{ width: "auto", height: "20vh" }} // 비율 유지를 위한 CSS 추가
                  priority // LCP 이미지에 priority 속성 추가
                />
              </div>
              <div>
                <Image
                  src="/eximg2.webp"
                  alt="Example Image 2"
                  width={800}
                  height={300}
                  style={{ width: "auto", height: "20vh" }}
                />
              </div>
              <div>
                <Image
                  src="/eximg3.jpg"
                  alt="Example Image 3"
                  width={800}
                  height={300}
                  style={{ width: "auto", height: "20vh" }}
                />
              </div>
            </styles.Slid>
          </styles.SlideshowContainer>
        </styles.Mypagebox>
      </styles.Container>
    </>
  );
};

export default Main;
