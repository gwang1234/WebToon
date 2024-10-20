import styled from "styled-components";
import Slider from "react-slick";

const Container = styled.div`
  width: 99vw;
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
  height: 150vh;
  border-radius: 10%;
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
  pointer-events: none;
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

// 전체 댓글 리스트를 감싸는 컨테이너 스타일
const CommentList = styled.div`
  margin-top: 2%;
  padding: 1%;
  border-radius: 8px;
  max-width: 80%;
  margin-left: 10%;
  border: 1px solid #000;
  background-color: white;
`;

const CommentLists = styled.div`
  display: grid;
  grid-template-columns: 2fr 0fr 8fr; /* 웹툰 제목, 구분선, 댓글 내용 */
  gap: 5%; /* 열 간격 */
  align-items: center; /* 수직 정렬 */
  padding: 10px;
  margin-bottom: 10px;
  border-bottom: 1px solid #ddd;
  background-color: #ddd;
  &:last-child {
    border-bottom: none;
  }
`;

// 각 댓글 항목의 스타일
const CommentItem = styled.div`
  display: grid;
  grid-template-columns: 2fr 0fr 8fr; /* 웹툰 제목, 구분선, 댓글 내용 */
  gap: 5%; /* 열 간격 */
  align-items: center; /* 수직 정렬 */
  padding: 10px;
  margin-bottom: 10px;
  border-bottom: 1px solid #ddd;
  &:last-child {
    border-bottom: none;
  }
`;

// 댓글 작성자 이름 스타일
const CommentUserName = styled.div`
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
`;

// 댓글 내용 스타일
const CommentContent = styled.div`
  color: #555;
  font-size: 20px;
  margin-bottom: 10px;
  white-space: nowrap; /* 텍스트를 한 줄로 강제 */
  overflow: hidden; /* 넘친 텍스트를 숨김 */
  text-overflow: ellipsis; /* 넘친 텍스트에 줄임표(...) 적용 */
`;

// 웹툰 제목 스타일
const CommentWebtoonTitle = styled.div`
  font-size: 15px;
  color: #333;
  font-style: italic;
  white-space: nowrap; /* 텍스트를 한 줄로 강제 */
  overflow: hidden; /* 넘친 텍스트를 숨김 */
  text-overflow: ellipsis; /* 넘친 텍스트에 줄임표(...) 적용 */
`;

// 구분선 스타일
const CommentMiddle = styled.div`
  width: 1px;
  height: 100%; /* 댓글 항목 높이에 맞춰 */
  background-color: #555; /* 구분선 색상 */
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
  CommentList,
  CommentItem,
  CommentUserName,
  CommentContent,
  CommentWebtoonTitle,
  CommentMiddle,
  CommentLists,
};
