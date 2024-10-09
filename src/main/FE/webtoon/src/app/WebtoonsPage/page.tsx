"use client"; // 클라이언트 컴포넌트로 지정

import { useState } from "react";
import WebtoonsPageLick from "./components/webtoonPageLike";
import WebtoonsPageView from "./components/webtoonPageView";
import WebtoonsAll from "./components/webtoonPageAll"; // 새로운 WebtoonsAll 컴포넌트 추가
import * as styles from "./styles/mainStyles";

export default function WebtoonsPage() {
  // state를 사용하여 어떤 버튼이 눌렸는지 관리
  const [activePage, setActivePage] = useState<"like" | "view" | "all">("like");

  return (
    <>
      <styles.Container>
        <styles.ButtonContainer>
          <styles.Button
            isActive={activePage === "like"}
            onClick={() => setActivePage("like")}
          >
            추천수 별 웹툰
          </styles.Button>
          <styles.Button
            isActive={activePage === "view"}
            onClick={() => setActivePage("view")}
          >
            좋아요 별 웹툰
          </styles.Button>
          <styles.Button
            isActive={activePage === "all"}
            onClick={() => setActivePage("all")}
          >
            전체 웹툰
          </styles.Button>
        </styles.ButtonContainer>

        {/* activePage에 따라 다른 컴포넌트 표시 */}
        {activePage === "like" && <WebtoonsPageLick />}
        {activePage === "view" && <WebtoonsPageView />}
        {activePage === "all" && <WebtoonsAll />}
      </styles.Container>
    </>
  );
}
