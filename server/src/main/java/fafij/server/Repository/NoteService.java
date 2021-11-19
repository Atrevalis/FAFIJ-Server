package fafij.server.Repository;
import fafij.server.entity.Note;
import fafij.server.service.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
    public void deleteNote(Note note) {
        noteRepository.delete(note);
    }

    public List<Note> findAllByType(Boolean type){
        return noteRepository.findAllByType(type);
    }
    public List<Note> findAllById(Iterable<Long> id){
        return noteRepository.findAllById(id);
    }

}
