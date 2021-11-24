package fafij.server.Repository;
import fafij.server.entity.Category;
import fafij.server.entity.Note;
import fafij.server.service.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    @Autowired
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public void createNote(Note note) {
        noteRepository.save(note);
    }
    public void deleteNote(Optional<Note> note) {
        noteRepository.delete(note.get());
    }

    public List<Note> findAllByCategory(Category category){
        return noteRepository.findAllByIdCtgr(category);
    }
    public Optional<Note> findById(Long id){
        return noteRepository.findById(id);
    }

}
