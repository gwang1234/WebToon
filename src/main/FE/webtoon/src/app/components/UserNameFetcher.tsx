// UserNameFetcher.tsx

import { useEffect } from "react";
import axios from "axios";

interface UserNameFetcherProps {
  onFetchSuccess: (userName: string) => void;
}

export const UserNameFetcher: React.FC<UserNameFetcherProps> = ({
  onFetchSuccess,
}) => {
  useEffect(() => {
    const fetchUserName = async () => {
      try {
        const token = sessionStorage.getItem("token");
        const email = sessionStorage.getItem("email") || "";
        const provider_id = sessionStorage.getItem("provider_id");

        // token이 있을 경우 Authorization 헤더 추가
        const headers = token ? { Authorization: `Bearer ${token}` } : {};

        const response = await axios.post(
          `${process.env.NEXT_PUBLIC_API_URL}/users`,
          {
            provider_id: provider_id,
            email: email,
          },
          { headers }
        );

        const { userName } = response.data;
        onFetchSuccess(userName || ""); // userName 전달
      } catch (error) {
        console.error("userName을 가져오는 중 오류 발생:", error);
      }
    };

    fetchUserName();
  }, [onFetchSuccess]);

  return null;
};
