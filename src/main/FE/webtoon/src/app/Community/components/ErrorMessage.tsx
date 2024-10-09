import React from "react";
import * as styles from "../styles/mainStyles";

interface ErrorMessageProps {
  message: string;
}

const ErrorMessage: React.FC<ErrorMessageProps> = ({ message }) => {
  return <styles.ErrorMessage>{message}</styles.ErrorMessage>;
};

export default ErrorMessage;
