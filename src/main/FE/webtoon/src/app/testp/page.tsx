"use client";

import { useSpring, animated } from "@react-spring/web";
import { useState } from "react";
import styled from "styled-components";

// styled-components로 스타일 정의
const MenuContainer = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
`;

const StyledButton = styled.button`
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
`;

const MenuList = styled(animated.div)`
  top: 40px;
  bottom: 70px;
  left: 0;
  background-color: white;
  border-radius: 5px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 10px;
  z-index: 10;
  width: 300px; /* 메뉴의 너비 증가 */
  height: auto; /* 높이는 내용에 따라 자동 조정 */

  ul {
    list-style: none;
    padding: 0;
    margin: 0;
  }

  li {
    margin: 0;
    padding: 20px 30px; /* 항목 크기 조정 */
    cursor: pointer;
    font-size: 18px; /* 글자 크기 키우기 */
    &:hover {
      background-color: #f1f1f1;
    }
  }
`;

const HiddenMenu: React.FC = () => {
  const [isOpen, setIsOpen] = useState(false);

  // 더 자연스러운 애니메이션을 위해 config 설정 추가
  const styles = useSpring({
    opacity: isOpen ? 1 : 0,
    transform: isOpen ? "translateY(0)" : "translateY(-30px)",
    config: {
      tension: 220, // 스프링의 팽팽함 (높을수록 빨라짐)
      friction: 20, // 마찰 (높을수록 부드러움)
      clamp: true, // 애니메이션 끝에서 고정
    },
  });

  return (
    <MenuContainer
      onMouseEnter={() => setIsOpen(true)}
      onMouseLeave={() => setIsOpen(false)}
    >
      <StyledButton>My account</StyledButton>
      {isOpen && (
        <MenuList style={styles}>
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
        </MenuList>
      )}
    </MenuContainer>
  );
};

export default HiddenMenu;
