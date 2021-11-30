package fafij.server.service;

import fafij.server.entity.Category;
import fafij.server.entity.Journal;
import fafij.server.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByIdCtgr(Category category);
    Optional<Note> findById(Long id);
    List<Note> findAllByIdJournal(Journal journal);
    List<Note> findAllByIdJournalOrderByDateDesc(Journal journal);
}