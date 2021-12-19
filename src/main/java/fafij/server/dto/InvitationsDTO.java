package fafij.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fafij.server.entity.Invitations;
import fafij.server.entity.Journal;
import fafij.server.entity.Roles;
import fafij.server.entity.Users;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@EnableTransactionManagement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvitationsDTO {
    @JsonProperty("journalName")
    private JournalDTO journal;
    @JsonProperty("role")
    private RoleDTO idRole;
    private Boolean accept;

    @Transactional
    public List<InvitationsDTO> getInvitationsDTOList(List<Invitations> invitationsList){
        List<InvitationsDTO> invitationsDTOList = new ArrayList<>();

        for(Invitations invitations : invitationsList){
            if(!invitations.getAccepted() && !invitations.getDeclined()){
                journal = new JournalDTO();
                idRole = new RoleDTO();
                InvitationsDTO invitationsDTO = new InvitationsDTO();
                journal.setName(invitations.getIdJournal().getName());
                idRole.setName(invitations.getIdRole().getRoleName());
                invitationsDTO.setJournal(journal);
                invitationsDTO.setIdRole(idRole);
                invitationsDTOList.add(invitationsDTO);
            }
        }
        return invitationsDTOList;
    }

    public JournalDTO getJournal() {
        return journal;
    }

    public void setJournal(JournalDTO journal) {
        this.journal = journal;
    }

    public RoleDTO getIdRole() {
        return idRole;
    }

    public void setIdRole(RoleDTO idRole) {
        this.idRole = idRole;
    }

    public Boolean getAccept() {
        return accept;
    }

    public InvitationsDTO(JournalDTO journal, RoleDTO idRole, Boolean accept) {
        this.journal = journal;
        this.idRole = idRole;
        this.accept = accept;
    }

    public InvitationsDTO() {
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }
}
