package fafij.server.Repository;
import fafij.server.entity.Users;
import fafij.server.service.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public void createUsers(Users users) {
        usersRepository.save(users);
        //usersRepository.registration(users.getLogin(),users.getPassword());
    }

    public List<Users> findAllByLogin(String login){
        return usersRepository.findAllByLogin(login);
    }
    public List<Users> findAllById(Iterable<Long> id){
        return usersRepository.findAllById(id);
    }
}