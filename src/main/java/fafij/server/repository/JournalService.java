package fafij.server.repository;
import fafij.server.entity.Journal;
import fafij.server.entity.UserRoles;
import fafij.server.entity.Users;
import fafij.server.service.*;
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
    private RolesRepository rolesRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private InvitationsRepository invitationsRepository;

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
    public void setUserRoles(String user, String journal, String role){
        Long id_user = usersRepository.findByLogin(user).getId();
        Long id_jrnl = journalRepository.findByName(journal).getId();
        Long id_role = rolesRepository.findByRoleName(role).getId();
        userRolesRepository.setUserRoles(id_user, id_jrnl, id_role);
    }

    public Boolean checkUserRole(String login, String journalName){
        return !userRolesRepository.existsByIdUserAndAndIdJournal(usersRepository.findByLogin(login), journalRepository.findByName(journalName));
    }

    public Boolean existsUser(String login){
        return usersRepository.existsByLogin(login);
    }

    public Boolean checkUserInvite(String login, String journalName){
        return !invitationsRepository.existsByIdUserAndIdJournal(usersRepository.findByLogin(login), journalRepository.findByName(journalName));
    }

    public Boolean checkUserDecline(String login, String journalName){
        return !invitationsRepository.existsByIdUserAndIdJournalAndDeclined(usersRepository.findByLogin(login), journalRepository.findByName(journalName), true);
    }

    public UserRoles findByUserAndJournal(String user, String journal){
        Users users = usersRepository.findByLogin(user);
        Journal jrnl = journalRepository.findByName(journal);
        return userRolesRepository.findByIdUserAndIdJournal(users, jrnl);
    }

    public void addInvitation(String login, String journalName, String roleName){
        Long id_user = usersRepository.findByLogin(login).getId();
        Long id_jrnl = journalRepository.findByName(journalName).getId();
        Long id_role = rolesRepository.findByRoleName(roleName).getId();
        invitationsRepository.addInvitation(id_user, id_jrnl, id_role);
    }
}
