package fafij.server.service;

import fafij.server.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {
    List<Journal> findAllByName(String name);
    List<Journal> findAll();
    Journal findByName(String name);
}