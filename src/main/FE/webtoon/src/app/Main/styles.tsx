import styled from "styled-components";

// 컨테이너: 화면 전체 크기를 차지하는 배경 스타일
const Container = styled.div`
  position: relative;
  width: 99vw;
  height: auto;
  background: #ffffff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const MainBanner = styled.div`
  position: relative;
  width: 60vw;
  height: 40vh;
  background-color: #f0f0f0;
`;

const BannerImage = styled.img`
  width: 100%;
  height: 100%;
`;

const Rating = styled.div`
  font-size: 2.5vh;
  font-weight: bold;
  color: red;
`;

const Top10Section = styled.section`
  margin-top: 2vh;
  width: 50vw;
  h2 {
    font-size: 3vh;
    margin-bottom: 0.1vh;
  }
`;

// 슬라이더 스타일 (스크롤 제거)
const Slider = styled.div`
  display: flex;
  overflow: hidden; /* 스크롤 제거 */
  height: 30vh;
`;

const WebtoonCard = styled.div`
  margin-right: 1vw;
  width: 7vw;
  height: 20vh;
  p {
    text-align: center;
    font-size: 2vh;
    margin-top: 0.5vh;
  }
`;

const NextArrow = styled.div`
  display: block;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  z-index: 1;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;

  &:hover {
    background: rgba(0, 0, 0, 0.8);
  }

  &::before {
    content: "→";
    font-size: 18px;
  }
`;

// Prev Arrow 스타일
const PrevArrow = styled.div`
  display: block;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
  z-index: 1;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;

  &:hover {
    background: rgba(0, 0, 0, 0.8);
  }

  &::before {
    content: "←";
    font-size: 18px;
  }
`;

const components = {
  Container,
  MainBanner,
  BannerImage,
  Rating,
  Top10Section,
  Slider,
  WebtoonCard,
  NextArrow,
  PrevArrow,
};

export default components;
