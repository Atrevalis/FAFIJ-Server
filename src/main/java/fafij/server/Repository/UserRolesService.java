package fafij.server.Repository;
import fafij.server.entity.Journal;
import fafij.server.entity.Roles;
import fafij.server.entity.UserRoles;
import fafij.server.entity.Users;
import fafij.server.service.JournalRepository;
import fafij.server.service.RolesRepository;
import fafij.server.service.UserRolesRepository;
import fafij.server.service.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRolesService {

    @Autowired
    private final UserRolesRepository userRolesRepository;
    private UsersRepository userRepository;
    private JournalRepository journalRepository;
    private RolesRepository rolesRepository;

    public UserRolesService(UserRolesRepository userRolesRepository){
        this.userRolesRepository = userRolesRepository;
    }

    public void addUser(Users user, Journal journal, Roles role){
        UserRoles userRoles = new UserRoles();
        userRoles.setIdUser(user);
        userRoles.setIdJournal(journal);
        userRoles.setIdRole(role);
        userRolesRepository.save(userRoles);
    }

    public List<UserRoles> findAllByRole(UserRoles role){
        Roles id = role.getIdRole();
        return userRolesRepository.findAllByIdRole(id);
    }

    public List<UserRoles> findAllByJournal(UserRoles jrnl){
        Journal id = jrnl.getIdJournal();
        return userRolesRepository.findAllByIdJournal(id);
    }

    public UserRoles findByUser(String user){
        Users id = userRepository.findByLogin(user);
        return userRolesRepository.findByIdUser(id);
    }

    public void updateUserRoles(String user, String role){
        Roles roleName = rolesRepository.findByRoleName(role);
        UserRoles entity = findByUser(user);
        entity.setIdRole(roleName);
    }

}