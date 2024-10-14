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

    if (storedUserName) {
      setName(storedUserName);
    } else {
      setName(null);
    }
    setIsLoading(false);
  };

  // 세션 업데이트 감지
  useEffect(() => {
    fetchUserData(); // 페이지 로드 시 세션 확인
    console.log("커스텀 감지");

    const handleSessionUpdate = () => {
      console.log("sessionUpdated 이벤트 감지됨");
      fetchUserData();
    };

    window.addEventListener("sessionUpdated", handleSessionUpdate);

    // 컴포넌트 언마운트 시 이벤트 리스너 제거
    return () => {
      window.removeEventListener("sessionUpdated", handleSessionUpdate);
    };
  }, []);

  // 로그아웃 처리
  const handleLogout = () => {
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("refreshToken");
    sessionStorage.removeItem("email");
    sessionStorage.removeItem("userName");
    setName(null); // 로그아웃 후 바로 상태를 업데이트
    router.push("/login");
    window.dispatchEvent(new Event("sessionUpdated")); // 로그아웃 후에도 이벤트 발생
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
