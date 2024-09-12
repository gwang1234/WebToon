package org.example.webtoonepics.repository;

import org.example.webtoonepics.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Boolean existsByEmail(String email);
    Boolean existsByUserName(String username);
    User findByEmail(String email);
}
