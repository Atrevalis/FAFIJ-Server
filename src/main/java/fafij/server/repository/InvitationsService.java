package fafij.server.repository;

import fafij.server.entity.Invitations;
import fafij.server.entity.Journal;
import fafij.server.entity.Users;
import fafij.server.service.InvitationsRepository;
import fafij.server.service.JournalRepository;
import fafij.server.service.RolesRepository;
import fafij.server.service.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitationsService {
    @Autowired
    private final InvitationsRepository invitationsRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private RolesRepository rolesRepository;

    public InvitationsService(InvitationsRepository invitationsRepository){
        this.invitationsRepository = invitationsRepository;
    }

    public void addInvitation(String login, String journalName, String roleName){
        Long id_user = usersRepository.findByLogin(login).getId();
        Long id_jrnl = journalRepository.findByName(journalName).getId();
        Long id_role = rolesRepository.findByRoleName(roleName).getId();
        invitationsRepository.addInvitation(id_user, id_jrnl, id_role);
    }

    public Invitations findByUserAndJournalAndAcceptAndDeclined(String login, String journalName){
        Users user = usersRepository.findByLogin(login);
        Journal journal = journalRepository.findByName(journalName);
        return invitationsRepository.findByIdUserAndIdJournalAndAcceptedAndDeclined(user, journal, false, false);
    }

    public void updateStatus(Invitations invitations){
        invitations.setAccepted(true);
        invitationsRepository.save(invitations);
    }

    public void delete(Invitations invitations){
        invitationsRepository.delete(invitations);
    }

    public Boolean checkUser(String login, String journalName){
        return !invitationsRepository.existsByIdUserAndIdJournal(usersRepository.findByLogin(login), journalRepository.findByName(journalName));
    }
}
