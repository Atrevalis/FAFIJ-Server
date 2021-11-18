package fafij.server.service;

import fafij.server.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByLogin(String login);
    List<Users> findAllByLogin(String login);
    Users findAllById(Long id);

    @Modifying
    @Query(value = "insert into user (login, password) VALUES (:login,:password)", nativeQuery = true)
    @Transactional
    void registration(@Param("login") String login, @Param("password") String password);
}
