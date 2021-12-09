package fafij.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fafij.server.entity.Journal;
import fafij.server.entity.Note;
import fafij.server.entity.Roles;
import fafij.server.entity.Users;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@EnableTransactionManagement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JournalDTO {

    @JsonProperty("journalName")
    private String name;
    @JsonProperty("logins")
    private List<UsersDTO> user;
    private List<Note> idNote;
    private List<Roles> roles;

    @Transactional
    public List<JournalDTO> getJournalDTOList(List<Journal> journalList){
        List<JournalDTO> journalDTOList = new ArrayList<>();

        for(Journal journal : journalList){
            user = new ArrayList<>();
            JournalDTO journalDTO = new JournalDTO();
            journalDTO.setName(journal.getName());

            for (Users users : journal.getUser()){
                UsersDTO usersDTO = new UsersDTO();
                usersDTO.setLogin(users.getLogin());
                user.add(usersDTO);
            }
            journalDTO.setUser(user);
            journalDTOList.add(journalDTO);
        }
        return journalDTOList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UsersDTO> getUser() {
        return user;
    }

    public void setUser(List<UsersDTO> user) {
        this.user = user;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public JournalDTO(String name, List<UsersDTO> user, List<Note> idNote, List<Roles> roles) {
        this.name = name;
        this.user = user;
        this.idNote = idNote;
        this.roles = roles;
    }

    public JournalDTO() {
    }
}
