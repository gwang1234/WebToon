"use client";

import Head from "next/head";
import Image from "next/image";
import * as styles from "./styles";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { useEffect, useState } from "react";
import axios from "axios";
import { UserNameFetcher } from "../components/UserNameFetcher";

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

// Comment 타입 정의
type Comment = {
  id: number;
  userName: string;
  content: string;
  webtoon_title: string; // 웹툰 제목 추가
};

type community = {
  id: number;
  title: string;
  content: string;
};

type C_community = {
  id: number;
  title: string;
  content: string;
};

const Main: React.FC = () => {
  const [mypage, setMypage] = useState<Mypage | null>(null);
  const [webtoons, setWebtoons] = useState<Webtoon[]>([]);
  const [comments, setComments] = useState<Comment[]>([]); // 댓글 상태
  const [communites, setCommunites] = useState<community[]>([]); // 커뮤 상태
  const [c_comments, setC_comments] = useState<C_community[]>([]); // 커뮤댓글 상태
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [userName, setUserName] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [confirmPassword, setConfirmPassword] = useState<string>("");
  const [isTokenPresent, setIsTokenPresent] = useState<boolean>(false);
  const [isProviderIdPresent, setIsProviderIdPresent] =
    useState<boolean>(false);
  const [fetchUserName, setFetchUserName] = useState<boolean>(false);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const token = sessionStorage.getItem("token");
        const provider_id = sessionStorage.getItem("provider_id") || null;
        const email = sessionStorage.getItem("email") || null;

        setIsTokenPresent(!!token);
        setIsProviderIdPresent(!!provider_id);

        // console.log(token);

        // token이 있을 경우 Authorization 헤더 추가
        const headers = token ? { Authorization: `Bearer ${token}` } : {};

        const [userRes, webtoonRes, commentRes, communityRes, C_commentRes] =
          await Promise.all([
            axios.post(
              `${process.env.NEXT_PUBLIC_API_URL}/users`,
              {
                provider_id: provider_id,
                email: email,
              },
              {
                headers,
              }
            ),
            axios.post(
              `${process.env.NEXT_PUBLIC_API_URL}/webtoons/like-user`,
              {
                provider_id: provider_id,
              },
              {
                headers,
              }
            ),
            axios.post(
              `${process.env.NEXT_PUBLIC_API_URL}/reviews/user`,
              {
                provider_id: provider_id,
              },
              {
                headers,
              }
            ),
            axios.post(
              `${process.env.NEXT_PUBLIC_API_URL}/community/user`,
              {
                provider_id: provider_id,
              },
              {
                headers,
              }
            ),
            axios.post(
              `${process.env.NEXT_PUBLIC_API_URL}/c-comment/user/comment`,
              {
                provider_id: provider_id,
              },
              {
                headers,
              }
            ),
          ]);

        setMypage(userRes.data);
        setUserName(userRes.data.userName);
        setWebtoons(webtoonRes.data);
        setComments(commentRes.data.content || []);
        setCommunites(communityRes.data.content || []);
        setC_comments(C_commentRes.data.content || []);
      } catch (err) {
        setError("데이터를 불러오는 중 오류가 발생했습니다.");
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const handleCorrection = async () => {
    const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{6,16}$/; // 영문, 숫자 포함 6~16자리 정규식

    // 입력된 비밀번호를 검증하는 함수
    const isPasswordValid = () => passwordRegex.test(password);

    // 비밀번호와 확인 비밀번호가 일치하는지 검증하는 함수
    const isPasswordConfirmed = () => password === confirmPassword;

    // 회원 정보를 업데이트하는 함수
    const updateUserInfo = async () => {
      const token = sessionStorage.getItem("token");
      const provider_id = sessionStorage.getItem("provider_id");
      const headers = token ? { Authorization: `Bearer ${token}` } : {};

      try {
        await axios.put(
          `${process.env.NEXT_PUBLIC_API_URL}/users`,
          {
            userName: userName,
            password: isTokenPresent ? password : null,
            provider_id: provider_id,
          },
          { headers }
        );

        alert("회원 정보가 수정되었습니다.");

        setFetchUserName(true); // UserNameFetcher 호출
      } catch {
        alert("회원 정보 수정 중 오류가 발생했습니다.");
      }
    };

    // 조건에 따른 사용자 피드백 제공 및 업데이트 실행
    if (password !== "" || isProviderIdPresent) {
      if (!isPasswordValid()) {
        alert("비밀번호는 영어와 숫자를 포함하여 6~16자리로 설정해야 합니다.");
        return;
      }

      if (!isPasswordConfirmed()) {
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다");
        return;
      }

      await updateUserInfo();
    } else {
      alert("비밀번호를 입력해 주세요");
    }
  };

  const sliderSettings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: Math.min(webtoons.length, 3),
    slidesToScroll: 3,
  };

  return (
    <>
      <Head>
        <title>마이페이지</title>
      </Head>

      <styles.Container>
        {loading && <p>로딩 중...</p>}
        {error && <p>{error}</p>}
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

              {isTokenPresent && (
                <>
                  <styles.Id>
                    <styles.IdText>비밀번호</styles.IdText>
                    <styles.IdInput
                      type="password"
                      placeholder="******"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                    />
                  </styles.Id>
                  <styles.Id>
                    <styles.IdText>비밀번호 확인</styles.IdText>
                    <styles.IdInput
                      type="password"
                      placeholder="******"
                      value={confirmPassword}
                      onChange={(e) => setConfirmPassword(e.target.value)}
                    />
                  </styles.Id>
                </>
              )}

              {!isTokenPresent && isProviderIdPresent && (
                <p>소셜 로그인 사용자는 닉네임만 수정할 수 있습니다.</p>
              )}
            </styles.NameAndId>

            <styles.Email>
              <styles.EmailText>이메일</styles.EmailText>
              <styles.EmailInput value={mypage.email || ""} readOnly />
            </styles.Email>

            <styles.CorrectionButton onClick={handleCorrection}>
              수정하기
            </styles.CorrectionButton>

            <styles.Webtoon>관심 작품</styles.Webtoon>

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
                        style={{ width: "auto", height: "20vh" }}
                        priority
                      />
                    </div>
                  ))}
                </styles.Slid>
              </styles.SlideshowContainer>
            ) : (
              <p>관심 작품이 없습니다.</p>
            )}

            <styles.Webtoon>내가 작성한 웹툰 댓글</styles.Webtoon>

            {comments.length > 0 ? (
              <styles.CommentList>
                {/* <styles.CommentLists>
                  <styles.CommentWebtoonTitle>제목</styles.CommentWebtoonTitle>
                  <styles.CommentMiddle />
                  <styles.CommentContent>내용</styles.CommentContent>
                </styles.CommentLists> */}

                {comments.map((comment) => (
                  <styles.CommentItem key={comment.id}>
                    {/* 웹툰 제목 추가 */}
                    <styles.CommentWebtoonTitle>
                      {comment.webtoon_title}
                    </styles.CommentWebtoonTitle>
                    <styles.CommentMiddle />
                    <styles.CommentContent>
                      {comment.content}
                    </styles.CommentContent>
                  </styles.CommentItem>
                ))}
              </styles.CommentList>
            ) : (
              <p>댓글이 없습니다.</p>
            )}

            <styles.Webtoon>내가 작성한 커뮤니티</styles.Webtoon>

            {communites.length > 0 ? (
              <styles.CommentList>
                {/* <styles.CommentLists>
                  <styles.CommentWebtoonTitle>제목</styles.CommentWebtoonTitle>
                  <styles.CommentMiddle />
                  <styles.CommentContent>내용</styles.CommentContent>
                </styles.CommentLists> */}

                {communites.map((community) => (
                  <styles.CommentItem key={community.id}>
                    {/* 웹툰 제목 추가 */}
                    <styles.CommentWebtoonTitle>
                      {community.title}
                    </styles.CommentWebtoonTitle>
                    <styles.CommentMiddle />
                    <styles.CommentContent>
                      {community.content}
                    </styles.CommentContent>
                  </styles.CommentItem>
                ))}
              </styles.CommentList>
            ) : (
              <p>커뮤니티가 없습니다.</p>
            )}

            <styles.Webtoon>내가 작성한 커뮤니티 댓글</styles.Webtoon>

            {c_comments.length > 0 ? (
              <styles.CommentList>
                {/* <styles.CommentLists>
                  <styles.CommentWebtoonTitle>제목</styles.CommentWebtoonTitle>
                  <styles.CommentMiddle />
                  <styles.CommentContent>내용</styles.CommentContent>
                </styles.CommentLists> */}

                {c_comments.map((C_community) => (
                  <styles.CommentItem key={C_community.id}>
                    {/* 웹툰 제목 추가 */}
                    <styles.CommentWebtoonTitle>
                      {C_community.title}
                    </styles.CommentWebtoonTitle>
                    <styles.CommentMiddle />
                    <styles.CommentContent>
                      {C_community.content}
                    </styles.CommentContent>
                  </styles.CommentItem>
                ))}
              </styles.CommentList>
            ) : (
              <p>커뮤니티가 없습니다.</p>
            )}
            {/* UserNameFetcher 컴포넌트 */}
            {fetchUserName && (
              <UserNameFetcher
                onFetchSuccess={(fetchedUserName) => {
                  sessionStorage.setItem("userName", fetchedUserName || "");
                  setUserName(fetchedUserName);
                  setFetchUserName(false); // Fetch 완료 후 상태 초기화
                  window.location.reload(); // 페이지 새로고침
                }}
              />
            )}
          </styles.Mypagebox>
        )}
      </styles.Container>
    </>
  );
};

export default Main;
