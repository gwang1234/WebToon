/* eslint-disable @typescript-eslint/no-explicit-any */
"use client";

import Head from "next/head";
import Image from "next/image";
import styles from "./styles";
import { useState } from "react";

const Main: React.FC = () => {
  const [top10] = useState<any[]>([
    {
      title: "화산귀환",
      imageUrl: "https://via.placeholder.com/100x150",
      width: 100,
      height: 150,
    },
    {
      title: "전지적 독자 시점",
      imageUrl: "https://via.placeholder.com/100x150",
      width: 100,
      height: 150,
    },
    {
      title: "엘리씨드",
      imageUrl: "https://via.placeholder.com/100x150",
      width: 100,
      height: 150,
    },
    {
      title: "나 혼자만 레벨업",
      imageUrl: "https://via.placeholder.com/100x150",
      width: 100,
      height: 150,
    },
    {
      title: "신의 탑",
      imageUrl: "https://via.placeholder.com/100x150",
      width: 100,
      height: 150,
    },
  ]);

  return (
    <>
      <Head>
        <title>회원가입</title>
      </Head>

      <styles.Container>
        <styles.MainBanner>
          {/* Main Banner Image */}
          <Image
            src="https://via.placeholder.com/800x300"
            alt="화산귀환"
            width={1000}
            height={500}
            priority
          />
        </styles.MainBanner>

        {/* Top 10 Section */}
        <styles.Top10Section>
          <h2>별점 탑 10</h2>
          <styles.Slider>
            {top10.map((webtoon, index) => (
              <styles.WebtoonCard key={index}>
                {/* Webtoon Image */}
                <Image
                  src={webtoon.imageUrl}
                  alt={webtoon.title}
                  width={webtoon.width}
                  height={webtoon.height}
                  priority
                />
                <p>{webtoon.title}</p>
              </styles.WebtoonCard>
            ))}
          </styles.Slider>
        </styles.Top10Section>
      </styles.Container>
    </>
  );
};

export default Main;
