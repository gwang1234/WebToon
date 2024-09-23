/** @type {import('next').NextConfig} */
const nextConfig = {
  compiler: {
    styledComponents: true,
  },
  images: {
    domains: ["via.placeholder.com"], // 외부 이미지 도메인 허용
  },
};

export default nextConfig;
