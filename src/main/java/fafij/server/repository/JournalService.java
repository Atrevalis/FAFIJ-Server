package fafij.server.repository;
import fafij.server.entity.Journal;
import fafij.server.service.JournalRepository;
import fafij.server.service.UserRolesRepository;
import fafij.server.service.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JournalService {
    @Autowired
    private final JournalRepository journalRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    public JournalService(JournalRepository journalRepository){
        this.journalRepository = journalRepository;
    }

    public void createJournal(String name) {
        Journal journal = new Journal();
        journal.setName(name);
        journalRepository.save(journal);
    }

    public List<Journal> findAllByName(String name){
        return journalRepository.findAllByName(name);
    }
    public List<Journal> findAllById(Long id){
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
    public Optional<Journal> findById(Long id){
        return journalRepository.findById(id);
    }
    public Boolean existsByNAme(String name){
        return !journalRepository.existsByName(name);
    }
}
