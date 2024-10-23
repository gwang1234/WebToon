"use client"; // 클라이언트 컴포넌트로 설정

import { useState, useEffect, Suspense } from "react";
import { usePathname } from "next/navigation"; // next/navigation에서 경로 감지 훅 사용
import Header from "./CommonLayout/Header/page";
import LoadingScreen from "./Loding/components/LoadingScreen"; // 로딩 컴포넌트 임포트

export default function ClientLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const [loading, setLoading] = useState(true); // 초기 로딩 상태를 true로 설정
  const pathname = usePathname(); // 경로를 감지

  useEffect(() => {
    setLoading(true); // 경로가 변경되면 로딩 상태 활성화

    const timer = setTimeout(() => {
      setLoading(false); // 페이지가 로드된 후 일정 시간 뒤에 로딩 해제
    }, 1000); // 1초 정도 로딩 시간 부여 (상황에 맞게 시간 조정 가능)

    return () => clearTimeout(timer); // 경로 변경 시 타이머 해제
  }, [pathname]); // 경로 변경을 감지

  return (
    <div>
      <Suspense fallback={<LoadingScreen />}>
        <Header />
        {/* 로딩 상태일 때만 로딩 화면을 보여주고, 로딩이 끝나면 실제 페이지를 표시 */}
        {loading ? (
          <LoadingScreen />
        ) : (
          <main
            style={{
              marginTop: "25vh", // Header 높이를 반영
              height: "calc(100vh - 25vh)", // Header 높이를 제외한 남은 공간
              overflowY: "auto", // 스크롤바는 여기에만 적용
            }}
          >
            {children}
          </main>
        )}
      </Suspense>
    </div>
  );
}
