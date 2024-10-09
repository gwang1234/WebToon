"use client";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Head from "next/head";
import Image from "next/image";
import Slider from "react-slick"; // react-slick의 Slider 컴포넌트 사용
import styles from "./styles";
import { useState, useEffect } from "react";
import axios from "axios";

// Webtoon 타입 정의
type Webtoon = {
  id: number;
  title: string;
  imageurl: string; // imageurl 필드를 정의
};

const Main: React.FC = () => {
  const [topLikeWebtoons, setTopLikeWebtoons] = useState<Webtoon[]>([]);
  const [topViewWebtoons, setTopViewWebtoons] = useState<Webtoon[]>([]);
  const [loadingLike, setLoadingLike] = useState(true); // 추천 웹툰 로딩 상태
  const [loadingView, setLoadingView] = useState(true); // 조회수 웹툰 로딩 상태

  useEffect(() => {
    // Like API를 통해 웹툰 목록 가져오기
    const fetchWebtoonsLike = async () => {
      try {
        const response = await axios.get(
          `${process.env.NEXT_PUBLIC_API_URL}/top-like`
        );
        setTopLikeWebtoons(response.data);
      } catch (error) {
        console.error(
          "추천 웹툰 목록을 가져오는 중 오류가 발생했습니다:",
          error
        );
      } finally {
        setLoadingLike(false); // 데이터 로드 완료 후 로딩 상태 변경
      }
    };

    // View API를 통해 웹툰 목록 가져오기
    const fetchWebtoonsView = async () => {
      try {
        const response = await axios.get(
          `${process.env.NEXT_PUBLIC_API_URL}/top-view`
        );
        setTopViewWebtoons(response.data);
      } catch (error) {
        console.error(
          "조회수 많은 웹툰 목록을 가져오는 중 오류가 발생했습니다:",
          error
        );
      } finally {
        setLoadingView(false); // 데이터 로드 완료 후 로딩 상태 변경
      }
    };

    fetchWebtoonsLike();
    fetchWebtoonsView();
  }, []);

  const sliderSettings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 5,
    slidesToScroll: 5,
    nextArrow: <styles.NextArrow />,
    prevArrow: <styles.PrevArrow />,
  };

  return (
    <>
      <Head>
        <title>웹툰 목록</title>
      </Head>

      <styles.Container>
        <styles.MainBanner>
          <Image
            src="/MainBanner.webp"
            alt="화산귀환"
            fill
            sizes="60vw"
            priority
          />
        </styles.MainBanner>

        <styles.Top10Section>
          <h2>추천 웹툰</h2>
          {loadingLike ? (
            <p>추천 웹툰 데이터를 불러오는 중입니다...</p> // 로딩 중 표시
          ) : (
            <Slider {...sliderSettings}>
              {topLikeWebtoons.map((webtoon, index) => (
                <styles.WebtoonCard key={index}>
                  <Image
                    src={webtoon.imageurl}
                    alt={webtoon.title}
                    width={100}
                    height={150}
                    priority
                  />
                </styles.WebtoonCard>
              ))}
            </Slider>
          )}
        </styles.Top10Section>

        <styles.Top10Section>
          <h2>조회수 많은 웹툰</h2>
          {loadingView ? (
            <p>조회수 많은 웹툰 데이터를 불러오는 중입니다...</p> // 로딩 중 표시
          ) : (
            <Slider {...sliderSettings}>
              {topViewWebtoons.map((webtoon, index) => (
                <styles.WebtoonCard key={index}>
                  <Image
                    src={webtoon.imageurl}
                    alt={webtoon.title}
                    width={100}
                    height={150}
                    priority
                  />
                </styles.WebtoonCard>
              ))}
            </Slider>
          )}
        </styles.Top10Section>
      </styles.Container>
    </>
  );
};

export default Main;
