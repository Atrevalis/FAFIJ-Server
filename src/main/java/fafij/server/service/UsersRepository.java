package fafij.server.service;

import fafij.server.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByLogin(String login);
    List<Users> findAllByLogin(String login);
    List<Users> findAll();
    Optional<Users> findById(Long id);
    Boolean existsByLogin(String login);
}
