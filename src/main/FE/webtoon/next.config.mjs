/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: false, // Strict Mode 비활성화
  compiler: {
    styledComponents: true,
  },
  images: {
    domains: ["via.placeholder.com", "www.kmas.or.kr"], // 허용할 이미지 도메인 추가
  },
};

export default nextConfig; // ES Module 방식으로 내보내기
