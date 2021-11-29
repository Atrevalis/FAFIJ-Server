package fafij.server.service;

import fafij.server.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {
    List<Journal> findAllByName(String name);
    List<Journal> findAll();
    Journal findByName(String name);
    Optional<Journal> findById(Long id);
    List<Journal> findAllById(Long id);
    Boolean existsByName(String name);
}