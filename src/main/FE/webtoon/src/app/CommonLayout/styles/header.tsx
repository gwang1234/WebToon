import styled from "styled-components";
import { animated } from "@react-spring/web";

const Container = styled.div`
  position: relative;
  width: 100%;
  background: #ffffff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const Headers = styled.div`
  display: flex;
  position: absolute;
  width: 100%;
  height: 13%;
  z-index: 101;
  background-color: #ffffff;
`;

const TopBar = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  padding: 0 5vw;
  height: 13vh;
`;

const Logo = styled.a`
  position: absolute;
  top: 2vh;
  left: 40%;
  transform: translateX(-50%);
`;

const Title = styled.a`
  position: absolute;
  left: 50%;
  top: 2vh;
  transform: translateX(-50%);
  margin-left: 2vw;
  font-family: "Inter";
  font-weight: 800;
  font-size: 3vw;
  line-height: 4vw;
  color: #000000;
  background-color: #ffffff;
`;

const NavLinks = styled.div`
  position: absolute;
  right: 5vw;
  top: 4.5vh;
  display: flex;
  gap: 2vw;
`;

const NavLink = styled.a`
  font-family: "Inter";
  font-weight: 700;
  font-size: 1vw;
  color: #5b5858;
  cursor: pointer;
  &:hover a {
    text-decoration: underline;
  }
`;

const NavBar = styled.div`
  position: absolute;
  width: 100vw;
  height: 9vh;
  left: 0;
  top: 13vh;
  background: #a9dfbf;
  display: flex;
  justify-content: space-between;
  padding: 0 20vw;
  align-items: center;
  z-index: 100;
`;

const NavBarMenu = styled.div`
  position: absolute;
  width: 100vw;
  height: 8vh;
  left: 0;
  top: 13vh;
  background: none;
  display: flex;
  justify-content: space-between;
  padding: 0 20vw;
  align-items: center;
`;

const NavItem = styled.a`
  font-family: "Inter";
  font-weight: 700;
  font-size: 1.3vw;
  color: #2c3e50;
  cursor: pointer;
  &:hover a {
    text-decoration: underline;
  }
`;

const MenuList = styled(animated.div)`
  top: 40%;
  left: 0;
  background-color: #a9dfbf;
  border-radius: 5px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin-top: 32.5%;
  padding: 5%;
  z-index: 10;
  width: 100%; /* 메뉴의 너비 증가 */
  height: auto; /* 높이는 내용에 따라 자동 조정 */

  ul {
    list-style: none;
    padding: 0;
    margin: 0;
  }

  li {
    margin: 0;
    padding: 2% 3%; /* 항목 크기 조정 */
    cursor: pointer;
    font-size: 18px; /* 글자 크기 키우기 */
    &:hover {
      background-color: #f1f1f1;
    }
  }
`;

const MenuContainer = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
`;

const HeaderComponents = {
  Container,
  TopBar,
  Logo,
  Title,
  NavLinks,
  NavLink,
  NavBar,
  NavBarMenu,
  NavItem,
  MenuList,
  MenuContainer,
  Headers,
};

export default HeaderComponents;
