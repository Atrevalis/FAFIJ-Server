package fafij.server.repository;
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
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private RolesRepository rolesRepository;

    public UserRolesService(UserRolesRepository userRolesRepository){
        this.userRolesRepository = userRolesRepository;
    }

    public List<UserRoles> findAllByRole(UserRoles role){
        Roles id = role.getIdRole();
        return userRolesRepository.findAllByIdRole(id);
    }

    public void setUserRoles(String user, String journal, String role){
        Long id_user = userRepository.findByLogin(user).getId();
        Long id_jrnl = journalRepository.findByName(journal).getId();
        Long id_role = rolesRepository.findByRoleName(role).getId();
        userRolesRepository.setUserRoles(id_user, id_jrnl, id_role);
    }

    public List<UserRoles> findAllByJournal(UserRoles jrnl){
        Journal id = jrnl.getIdJournal();
        return userRolesRepository.findAllByIdJournal(id);
    }

    public UserRoles findByUserAndJournal(String user, String journal){
        Users users = userRepository.findByLogin(user);
        Journal jrnl = journalRepository.findByName(journal);
        return userRolesRepository.findByIdUserAndIdJournal(users, jrnl);
    }

    public List<UserRoles> findAllByUser(String user){
        Users id = userRepository.findByLogin(user);
        return userRolesRepository.findAllByIdUser(id);
    }

    /*public void updateUserRoles(String user, String role){
        Roles roleName = rolesRepository.findByRoleName(role);
        UserRoles entity = findByUser(user);
        entity.setIdRole(roleName);
    }*/

}