import styled from "styled-components";

// 컨테이너: 화면 전체 크기를 차지하는 배경 스타일
const Container = styled.div`
  position: relative;
  width: 100vw;
  height: auto;
  margin-top: 8vh;
  background: #ffffff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const MainBanner = styled.div`
  position: relative;
  width: 100vw;
  height: 60vh;
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
  h2 {
    font-size: 3vh;
    margin-bottom: 0.1vh;
  }
`;

const Slider = styled.div`
  display: flex;
  overflow-x: scroll;
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

const components = {
  Container,
  MainBanner,
  BannerImage,
  Rating,
  Top10Section,
  Slider,
  WebtoonCard,
};

export default components;
