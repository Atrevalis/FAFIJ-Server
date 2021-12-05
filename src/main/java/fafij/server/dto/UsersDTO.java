package fafij.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import fafij.server.entity.Journal;
import fafij.server.entity.Roles;
import fafij.server.entity.Users;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@EnableTransactionManagement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersDTO {

    private String login;
    private List<JournalDTO> journal;
    private List<Roles> role;

    @Transactional
    public List<UsersDTO> getUsersDTOList(List<Users> usersList){
        List<UsersDTO> usersDTOList = new ArrayList<>();

        for(Users user : usersList){
            journal = new ArrayList<>();
            UsersDTO usersDTO = new UsersDTO();
            usersDTO.setLogin(user.getLogin());
            for(Journal journals : user.getJournal()){
                JournalDTO journalDTO = new JournalDTO();
                journalDTO.setName(journals.getName());
                journal.add(journalDTO);
            }
            usersDTO.setJournal(journal);
            usersDTOList.add(usersDTO);
        }
        return usersDTOList;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<JournalDTO> getJournal() {
        return journal;
    }

    public void setJournal(List<JournalDTO> journal) {
        this.journal = journal;
    }

    public List<Roles> getRole() {
        return role;
    }

    public void setRole(List<Roles> role) {
        this.role = role;
    }
}
