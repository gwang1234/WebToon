"use client";

import { useState, useEffect } from "react";
import Header from "../styles/header";
import Image from "next/image";
import { useRouter } from "next/navigation";

const HeaderComponent: React.FC = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [name, setName] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const router = useRouter();

  // 세션에서 사용자 정보 가져오기 함수
  const fetchUserData = () => {
    const storedUserName = sessionStorage.getItem("userName");

    console.log("세션에서 가져온 userName:", storedUserName); // 로그 추가

    if (storedUserName) {
      setName(storedUserName);
    } else {
      setName(null);
    }
    setIsLoading(false);
  };

  // 첫 렌더링 시 세션 정보 가져오기
  useEffect(() => {
    fetchUserData(); // 처음 페이지 로드 시 정보 가져오기

    // 세션이 업데이트되면 세션 정보를 다시 가져오기
    const handleSessionUpdate = () => {
      console.log("세션 업데이트 감지됨");
      fetchUserData();
    };

    // 커스텀 이벤트 리스너 등록
    window.addEventListener("sessionUpdated", handleSessionUpdate);

    // 컴포넌트 언마운트 시 이벤트 리스너 제거
    return () => {
      window.removeEventListener("sessionUpdated", handleSessionUpdate);
    };
  }, []);

  // name 상태가 변경될 때마다 확인하기 위한 useEffect
  useEffect(() => {
    if (name !== null) {
      console.log("확인: " + name); // name 값이 변경될 때 로그 출력
    }
  }, [name]); // name이 변경될 때마다 실행

  // 로그아웃 처리
  const handleLogout = () => {
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("refreshToken");
    sessionStorage.removeItem("email");
    sessionStorage.removeItem("userName");
    router.push("/login");
    fetchUserData(); // 로그아웃 후 세션 정보 갱신
  };

  if (isLoading) {
    return null; // 로딩 중일 때 아무것도 렌더링하지 않음
  }

  return (
    <Header.Container>
      <Header.Headers>
        <Header.Title href="/">웹툰 에픽스</Header.Title>
        <Header.TopBar>
          <Header.Logo href="/">
            <Image src="/logo.svg" alt="Logo" width={61} height={70} priority />
          </Header.Logo>

          {name ? (
            <Header.NavLinks>
              <span>{name}님 환영합니다!</span>
              <Header.NavLink as="button" onClick={handleLogout}>
                로그아웃
              </Header.NavLink>
            </Header.NavLinks>
          ) : (
            <Header.NavLinks>
              <Header.NavLink href="/join">회원가입</Header.NavLink>
              <Header.NavLink href="/login">로그인</Header.NavLink>
            </Header.NavLinks>
          )}
        </Header.TopBar>
      </Header.Headers>

      <Header.NavBar>
        <Header.NavItem href="/#">공지사항</Header.NavItem>
        <Header.NavItem href="/WebtoonsPage">웹툰 리뷰</Header.NavItem>
        <Header.NavItem href="/Community">커뮤니티</Header.NavItem>
        <Header.NavItem href="/MyPage">마이페이지</Header.NavItem>
      </Header.NavBar>

      <Header.NavBarMenu
        onMouseEnter={() => setIsOpen(true)}
        onMouseLeave={() => setIsOpen(false)}
      >
        {isOpen && (
          <Header.MenuList>
            <ul>
              <li>
                <a href="/settings">Settings</a>
              </li>
              <li>
                <a href="/support">Support</a>
              </li>
              <li>
                <a href="/license">License</a>
              </li>
            </ul>
          </Header.MenuList>
        )}
      </Header.NavBarMenu>
    </Header.Container>
  );
};

export default HeaderComponent;
