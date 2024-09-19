package org.example.webtoonepics.user.repository;

import java.util.Optional;
import org.example.webtoonepics.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Boolean existsByEmail(String email);

    Boolean existsByUserName(String username);

    Optional<User> findByEmail(String email);  // 이메일로 사용자 찾기
}
