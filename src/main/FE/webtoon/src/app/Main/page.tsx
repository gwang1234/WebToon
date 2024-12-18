"use client";

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Head from "next/head";
import Image from "next/image";
import Slider from "react-slick";
import styles from "./styles";
import { useState, useEffect } from "react";
import axios from "axios";
import { useRouter } from "next/navigation";

// Webtoon 타입 정의
type Webtoon = {
  id: number;
  title: string;
  imageurl: string;
};

const Main: React.FC = () => {
  const [topLikeWebtoons, setTopLikeWebtoons] = useState<Webtoon[]>([]);
  const [topViewWebtoons, setTopViewWebtoons] = useState<Webtoon[]>([]);
  const [loadingLike, setLoadingLike] = useState(true);
  const [loadingView, setLoadingView] = useState(true);
  const router = useRouter();

  useEffect(() => {
    // OAuth 로그인 시 provider_id 세션에 저장
    const searchParams = window.location.search;
    const providerId = new URLSearchParams(searchParams).get("username");
    const email = new URLSearchParams(searchParams).get("email");

    if (providerId) {
      sessionStorage.setItem("provider_id", providerId);
      console.log(
        "Provider ID (username) stored in sessionStorage:",
        providerId
      );
    }

    if (email) {
      sessionStorage.setItem("email", email);
      console.log("Email stored in sessionStorage:", email);
    }

    // 사용자 이름 가져오기
    const fetchUserName = async () => {
      try {
        const providerId = sessionStorage.getItem("provider_id");

        if (!providerId) {
          return;
        }

        const response = await axios.post(
          `${process.env.NEXT_PUBLIC_API_URL}/users`,
          { provider_id: providerId, email: email },
          {
            headers: {},
          }
        );

        const { userName } = response.data;
        sessionStorage.setItem("userName", userName || "");
      } catch (error) {
        console.error("userName을 가져오는 중 오류 발생:", error);
      }
    };

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
        setLoadingLike(false);
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
        setLoadingView(false);
      }
    };

    fetchUserName();
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

  // 클릭한 웹툰의 id로 이동
  const handleWebtoonClick = (id: number) => {
    router.push(`/WebtoonsPage/${id}`);
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
            <p>추천 웹툰 데이터를 불러오는 중입니다...</p>
          ) : (
            <Slider {...sliderSettings}>
              {topLikeWebtoons.map((webtoon) => (
                <styles.WebtoonCard
                  key={webtoon.title}
                  onClick={() => handleWebtoonClick(webtoon.id)}
                >
                  <Image
                    src={webtoon.imageurl}
                    alt={webtoon.title}
                    width={180}
                    height={230}
                    priority
                  />
                  <p>{webtoon.title}</p>
                </styles.WebtoonCard>
              ))}
            </Slider>
          )}
        </styles.Top10Section>

        <styles.Top10Section>
          <h2>조회수 많은 웹툰</h2>
          {loadingView ? (
            <p>조회수 많은 웹툰 데이터를 불러오는 중입니다...</p>
          ) : (
            <Slider {...sliderSettings}>
              {topViewWebtoons.map((webtoon) => (
                <styles.WebtoonCard
                  key={webtoon.title}
                  onClick={() => handleWebtoonClick(webtoon.id)}
                >
                  <Image
                    src={webtoon.imageurl}
                    alt={webtoon.title}
                    width={180}
                    height={230}
                    priority
                  />
                  <p>{webtoon.title}</p>
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
