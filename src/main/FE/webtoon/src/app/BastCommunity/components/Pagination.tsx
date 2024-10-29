import React from "react";
import * as styles from "../styles/mainStyles";

interface PaginationProps {
  totalPages: number;
  currentPage: number;
  onPageChange: (page: number) => void;
}

const Pagination: React.FC<PaginationProps> = ({
  totalPages,
  currentPage,
  onPageChange,
}) => {
  return (
    <styles.Pagination>
      {Array.from({ length: totalPages }, (_, index) => (
        <styles.PageButton
          key={index + 1}
          onClick={() => onPageChange(index + 1)}
          isActive={currentPage === index + 1}
        >
          {index + 1}
        </styles.PageButton>
      ))}
    </styles.Pagination>
  );
};

export default Pagination;
