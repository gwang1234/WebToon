"use client"; // 클라이언트 컴포넌트로 지정

import React from "react";
import styles from "../styles/styles";

type InputFieldProps = {
  type: string;
  name: string;
  placeholder: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  disabled?: boolean;
};

const InputField: React.FC<InputFieldProps> = ({
  type,
  name,
  placeholder,
  value,
  onChange,
  disabled,
}) => {
  return (
    <styles.InputField
      type={type}
      name={name}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      disabled={disabled}
    />
  );
};

export default InputField;
