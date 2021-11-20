package fafij.server.Repository;
import fafij.server.entity.Journal;
import fafij.server.entity.Roles;
import fafij.server.entity.UserRoles;
import fafij.server.entity.Users;
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

    public UserRolesService(UserRolesRepository userRolesRepository){
        this.userRolesRepository = userRolesRepository;
    }

    public void addUser(UserRoles role){
        userRolesRepository.save(role);
    }

    public List<UserRoles> findAllByRole(UserRoles role){
        Roles id = role.getIdRole();
        return userRolesRepository.findAllByIdRole(id);
    }

    public List<UserRoles> findAllByJournal(UserRoles jrnl){
        Journal id = jrnl.getIdJournal();
        return userRolesRepository.findAllByIdJournal(id);
    }

    public UserRoles findByUserJournal(UserRoles acc){
        Users userEntity = findByUser(acc).getIdUser();
        if (userEntity != null){
            if(acc.getIdJournal().equals(userEntity.getJournal())){
                return acc;
            }
        }
        return null;
    }

    public UserRoles findByUser(UserRoles user){
        Users id = user.getIdUser();
        return userRolesRepository.findByIdUser(id);
    }

    public void updateUserRoles(UserRoles user, UserRoles role){
        UserRoles entity = findByUser(user);
        entity.setIdRole(role.getIdRole());
    }

}