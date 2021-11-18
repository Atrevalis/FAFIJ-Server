package fafij.server.service;

import fafij.server.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findAllByLogin(String login);

    @Modifying
    @Query(value = "insert into users (Login, Password) VALUES (:Login,:Password)", nativeQuery = true)
    @Transactional
    void registration(@Param("Login") String Login, @Param("Password") String Password);
}
