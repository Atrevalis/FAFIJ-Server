package fafij.server.Repository;
import fafij.server.entity.Users;
import fafij.server.service.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Users findByLogin(String login){
        return usersRepository.findByLogin(login);
    }

    public List<Users> findAllByLogin(String login) {
        return usersRepository.findAllByLogin(login);
    }

    public Users findAllById(Long id){
        return usersRepository.findAllById(id);
    }

    public List<Users> findAllById(Iterable<Long> id){
        return usersRepository.findAllById(id);
    }
}