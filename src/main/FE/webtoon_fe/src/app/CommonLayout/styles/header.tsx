import styled from "styled-components";

const TopBar = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  padding: 0 5vw;
  height: 13vh;
`;

const Logo = styled.div`
  position: absolute;
  top: 2vh;
  left: 40%;
  transform: translateX(-50%);
`;

const Title = styled.h1`
  position: relative;
  left: 50%;
  bottom: 5%;
  transform: translateX(-50%);
  margin-left: 2vw;
  font-family: "Inter";
  font-weight: 800;
  font-size: 3vw;
  line-height: 4vw;
  color: #000000;
`;

const NavLinks = styled.div`
  position: absolute;
  right: 5vw;
  top: 4.5vh;
  display: flex;
  gap: 2vw;
`;

const NavLink = styled.span`
  font-family: "Inter";
  font-weight: 700;
  font-size: 1vw;
  color: #5b5858;
  cursor: pointer;
`;

const NavBar = styled.div`
  position: absolute;
  width: 100vw;
  height: 8vh;
  left: 0;
  top: 13vh;
  background: #a9dfbf;
  display: flex;
  justify-content: space-between;
  padding: 0 20vw;
  align-items: center;
`;

const NavItem = styled.span`
  font-family: "Inter";
  font-weight: 700;
  font-size: 1.3vw;
  color: #2c3e50;
  cursor: pointer;
`;

const HeaderComponents = {
  TopBar,
  Logo,
  Title,
  NavLinks,
  NavLink,
  NavBar,
  NavItem,
};

export default HeaderComponents;
