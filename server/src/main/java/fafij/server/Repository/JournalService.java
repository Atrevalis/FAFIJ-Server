package fafij.server.Repository;
import fafij.server.entity.Journal;
import fafij.server.entity.Users;
import fafij.server.service.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JournalService {
    @Autowired
    private final JournalRepository journalRepository;

    public JournalService(JournalRepository journalRepository){
        this.journalRepository = journalRepository;
    }

    public void createJournal(Journal journal) {
        journalRepository.save(journal);
    }

    public List<Journal> findAllByName(String name){
        return journalRepository.findAllByName(name);
    }
    public List<Journal> findAllById(Iterable<Long> id){
        return journalRepository.findAllById(id);
    }
    public Journal findByName(String name){
        return journalRepository.findByName(name);
    }
    public void deleteJournal(Journal journal) {
        journalRepository.delete(journal);
    }
    public List<Journal> findAll() {
        return journalRepository.findAll();
    }
}
