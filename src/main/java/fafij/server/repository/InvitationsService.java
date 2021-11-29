package fafij.server.repository;

import fafij.server.entity.Invitations;
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
        Invitations invite = new Invitations();
        invite.setIdUser(usersRepository.findByLogin(login));
        invite.setIdJournal(journalRepository.findByName(journalName));
        invite.setIdRole(rolesRepository.findByRoleName(roleName));
        invite.setAccepted(false);
        invitationsRepository.save(invite);
    }

    public List<Invitations> userInvitations(String login){
        return invitationsRepository.findAllByIdUserAndAccepted(usersRepository.findByLogin(login), false);
    }
}
