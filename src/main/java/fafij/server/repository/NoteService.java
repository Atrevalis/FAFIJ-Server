package fafij.server.repository;
import fafij.server.entity.*;
import fafij.server.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    private UserRolesRepository userRolesRepository;
    @Autowired
    private UsersRepository usersRepository;

    public NoteService(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public void createNote(String date, Long sum, String category, String comment, String journal) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date dates = format.parse(date);
        Note note = new Note();
        note.setDate(dates);
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

    public void updateNote(Long id, String date, Long sum, String category, String comment) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date dates = format.parse(date);
        Note note = noteRepository.findById(id).get();
        note.setDate(dates);
        note.setSum(sum);
        note.setIdCtgr(categoryRepository.findByNameAndIdJournal(category,noteRepository.findById(id).get().getIdJournal()));
        note.setComment(comment);
        noteRepository.save(note);
    }

    public List<Note> findAllByCategory(Category category){
        return noteRepository.findAllByIdCtgr(category);
    }
    public Optional<Note> findById(Long id){
        return noteRepository.findById(id);
    }

    public List<Note> findAllByJournal(String journal){
        return noteRepository.findAllByIdJournal(journalRepository.findByName(journal));
    }

    public List<Note> findAllByIdJournalOrderByDate(String journalName){
        return noteRepository.findAllByIdJournalOrderByDateDesc(journalRepository.findByName(journalName));
    }

    public UserRoles findByUserAndJournal(String user, String journal){
        Users users = usersRepository.findByLogin(user);
        Journal jrnl = journalRepository.findByName(journal);
        return userRolesRepository.findByIdUserAndIdJournal(users, jrnl);
    }
}
