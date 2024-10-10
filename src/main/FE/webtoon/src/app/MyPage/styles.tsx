import styled from "styled-components";
import Slider from "react-slick";

const Container = styled.div`
  width: 100vw;
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const Mypagebox = styled.div`
  display: flex;
  flex-direction: column;
  background: #a9dfbf50;
  width: 40vw;
  height: 75vh;
  border-radius: 10%;
  margin-top: 2vh;
`;

const Title = styled.div`
  margin: 3% 10%;
  font-size: 30px;
  font-weight: bold;
`;

const NameAndId = styled.div`
  display: flex;
`;

const Name = styled.div`
  display: flex;
  flex-direction: column;
`;

const NameText = styled.p`
  font-size: 25px;
  margin-top: 3%;
  margin-left: 25%;
`;

const NameInput = styled.input`
  padding: 1% 3%;
  font-size: 30px;
  width: 80%;
  border-radius: 30px;
  margin-left: 20%;
  margin-top: 3%;
`;

const Id = styled.div`
  display: flex;
  flex-direction: column;
`;

const IdText = styled.p`
  font-size: 25px;
  margin-top: 3%;
  margin-left: 15%;
`;

const IdInput = styled.input`
  padding: 1% 3%;
  font-size: 30px;
  width: 80%;
  border-radius: 30px;
  margin-left: 10%;
  margin-top: 3%;
`;

const Email = styled.div`
  display: flex;
  flex-direction: column;
`;

const EmailText = styled.p`
  font-size: 25px;
  margin-top: 3%;
  margin-left: 15%;
`;

const EmailInput = styled.input`
  padding: 1% 3%;
  font-size: 30px;
  width: 82%;
  border-radius: 30px;
  margin-left: 10%;
  margin-top: 3%;
`;

const CorrectionButton = styled.button`
  padding: 1% 3%;
  font-size: 30px;
  width: 60%;
  border-radius: 30px;
  margin-left: 20%;
  margin-top: 3%;
  background-color: #a9dfbf;
  border: none;
`;

const Webtoon = styled.div`
  font-size: 25px;
  margin-top: 3%;
  margin-left: 10%;
`;

const SlideshowContainer = styled.div`
  display: flex;
  position: relative;
  width: 80%;
  height: 20%;
  margin-left: 10%;
`;

const Slid = styled(Slider)`
  .slick-list {
    display: flex;
    justify-content: center; /* 슬라이더 리스트를 중앙 정렬 */
    width: 32vw;
  }

  .slick-list div {
    display: flex; /* 각 슬라이드에서 flex 사용 */
    justify-content: center; /* 슬라이드 내의 콘텐츠 중앙 정렬 */
    align-items: center; /* 수직 중앙 정렬 */
    width: 100%;
  }
  .slick-list img {
    display: block; /* 이미지 블록으로 설정 */
    margin: 0 auto; /* 좌우 여백 자동으로 설정하여 가운데 정렬 */
  }
`;

export {
  Container,
  Title,
  Mypagebox,
  NameAndId,
  Name,
  NameText,
  NameInput,
  Id,
  IdText,
  IdInput,
  Email,
  EmailText,
  EmailInput,
  CorrectionButton,
  Webtoon,
  SlideshowContainer,
  Slid,
};
