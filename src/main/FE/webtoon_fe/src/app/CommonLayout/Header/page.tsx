"use client";

import Header from "../styles/header";
import Image from "next/image";

const HeaderComponent: React.FC = () => {
  return (
    <>
      {/* 상단 영역 */}
      <Header.TopBar>
        <Header.Logo>
          <Image src="/logo.svg" alt="Logo" width={61} height={70} priority />
        </Header.Logo>
        <Header.Title>웹툰 에픽스</Header.Title>
        <Header.NavLinks>
          <Header.NavLink>회원가입</Header.NavLink>
          <Header.NavLink>로그인</Header.NavLink>
          <Header.NavLink>다크모드</Header.NavLink>
        </Header.NavLinks>
      </Header.TopBar>

      {/* 네비게이션 메뉴 */}
      <Header.NavBar>
        <Header.NavItem>공지사항</Header.NavItem>
        <Header.NavItem>웹툰 리뷰</Header.NavItem>
        <Header.NavItem>커뮤니티</Header.NavItem>
        <Header.NavItem>마이페이지</Header.NavItem>
      </Header.NavBar>
    </>
  );
};

export default HeaderComponent;
