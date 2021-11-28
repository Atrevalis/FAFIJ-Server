package fafij.server.repository;
import fafij.server.entity.Category;
import fafij.server.entity.Journal;
import fafij.server.entity.Note;
import fafij.server.service.CategoryRepository;
import fafij.server.service.JournalRepository;
import fafij.server.service.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    @Autowired
    private final NoteRepository noteRepository;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public NoteService(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public void createNote(String date, Long sum, String category, String comment, String journal) {
        Note note = new Note();
        note.setDate(date);
        note.setSum(sum);
        note.setIdCtgr(categoryRepository.findByNameAndIdJournal(category, journalRepository.findByName(journal)));
        note.setComment(comment);
        note.setIdJournal(journalRepository.findByName(journal));
        noteRepository.save(note);
    }
    public void deleteNote(Long note) {
        Optional<Note> n = noteRepository.findById(note);
        Note nn = n.get();
        noteRepository.delete(nn);
    }

    public List<Note> findAllByCategory(Category category){
        return noteRepository.findAllByIdCtgr(category);
    }
    public Optional<Note> findById(Long id){
        return noteRepository.findById(id);
    }

    public List<Note> findAllByJournal(String journal){
        Journal jrnl = journalRepository.findByName(journal);
        return noteRepository.findAllByIdJournal(jrnl);
    }
}
