package fafij.server.repository;
import fafij.server.entity.Journal;
import fafij.server.entity.Roles;
import fafij.server.entity.Users;
import fafij.server.service.JournalRepository;
import fafij.server.service.RolesRepository;
import fafij.server.service.UsersRepository;
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

    public void addJournal(String login, String journalName, String roleName){
        Users user = usersRepository.findByLogin(login);
        Journal journal = journalRepository.findByName(journalName);
        Roles role = rolesRepository.findByRoleName(roleName);
        user.getRole().add(role);
        user.getJournal().add(journal);
        usersRepository.save(user);
    }
}