package org.example.webtoonepics.user.repository;

import org.example.webtoonepics.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Boolean existsByEmail(String email);

    Boolean existsByUserName(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByProviderId(String providerId);  // 이메일로 사용자 찾기
}
