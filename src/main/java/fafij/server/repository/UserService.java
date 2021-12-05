package fafij.server.repository;
import fafij.server.entity.Invitations;
import fafij.server.entity.Journal;
import fafij.server.entity.Roles;
import fafij.server.entity.Users;
import fafij.server.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private final UsersRepository usersRepository;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private InvitationsRepository invitationsRepository;
    @Autowired
    private UserRolesRepository userRolesRepository;

    public UserService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public void createUsers(Users users) {
        usersRepository.save(users);
    }

    public List<Users> findAll(){
        return this.usersRepository.findAll();
    }

    public Users findByLogin(String login){
        return usersRepository.findByLogin(login);
    }

    public List<Users> findAllByLogin(String login) {
        return usersRepository.findAllByLogin(login);
    }

    public Optional<Users> findById(Long id){
        return usersRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Users u = findByLogin(login);
        if (Objects.isNull(u)) {
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }
        return new org.springframework.security.core.userdetails.User(u.getLogin(), u.getPassword(), true, true, true, true, new HashSet<>());
    }

    public Boolean existsUser(String login){
        return usersRepository.existsByLogin(login);
    }

    public Invitations findByUserAndJournalAndAcceptAndDeclined(String login, String journalName){
        Users user = usersRepository.findByLogin(login);
        Journal journal = journalRepository.findByName(journalName);
        return invitationsRepository.findByIdUserAndIdJournalAndAcceptedAndDeclined(user, journal, false, false);
    }

    public void setUserRoles(String user, String journal, String role){
        Long id_user = usersRepository.findByLogin(user).getId();
        Long id_jrnl = journalRepository.findByName(journal).getId();
        Long id_role = rolesRepository.findByRoleName(role).getId();
        userRolesRepository.setUserRoles(id_user, id_jrnl, id_role);
    }

    public void updateAccept(Invitations invitations){
        invitations.setAccepted(true);
        invitationsRepository.save(invitations);
    }

    public void updateDecline(Invitations invitations){
        invitations.setDeclined(true);
        invitationsRepository.save(invitations);
    }
}