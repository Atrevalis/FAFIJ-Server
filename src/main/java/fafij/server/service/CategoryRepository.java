package fafij.server.service;

import fafij.server.entity.Category;
import fafij.server.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByName(String name);
    List<Category> findAllByIdJournal(Journal journalName);
    Category findByName(String name);
    Category findByNameAndIdJournal(String name, Journal journalName);
    Boolean existsByNameAndIdJournal(String name, Journal journalName);
}