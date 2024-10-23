"use client"; // 클라이언트 컴포넌트로 지정

import { useState } from "react";
import * as styles from "../styles/mainStyles"; // 올바른 경로로 불러오기

type SearchBarProps = {
  onSearch: (searchType: string, searchKeyword: string) => void;
};

export default function SearchBar({ onSearch }: SearchBarProps) {
  const [searchType, setSearchType] = useState("title"); // 기본 검색 타입은 "title"
  const [searchKeyword, setSearchKeyword] = useState(""); // 검색 키워드

  // 검색 버튼 클릭 핸들러
  const handleSearch = () => {
    if (searchKeyword.trim() !== "") {
      onSearch(searchType, searchKeyword); // 부모 컴포넌트로 검색 요청 전달
    }
  };

  return (
    <styles.SearchContainer>
      <styles.Dropdown
        value={searchType}
        onChange={(e) => setSearchType(e.target.value)}
      >
        <option value="title">제목</option>
        <option value="content">내용</option>
        <option value="titleContent">제목+내용</option>
      </styles.Dropdown>
      <styles.SearchInput
        type="text"
        value={searchKeyword}
        onChange={(e) => setSearchKeyword(e.target.value)}
        placeholder="검색어를 입력하세요"
      />
      <styles.SearchButton onClick={handleSearch}>검색</styles.SearchButton>
    </styles.SearchContainer>
  );
}
