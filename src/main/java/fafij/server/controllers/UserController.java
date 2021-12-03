package fafij.server.controllers;

import fafij.server.dto.InvitationsDTO;
import fafij.server.entity.Invitations;
import fafij.server.repository.UserService;
import fafij.server.dto.JournalDTO;
import fafij.server.entity.Users;
import fafij.server.requestbodies.LoginJournal;
import fafij.server.requestbodies.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public void registration(@RequestBody Users user, HttpServletResponse response) {
        try {
            userService.createUsers(user);
            response.setStatus(HttpServletResponse.SC_CREATED);
       }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="/private/userJournals")
    public @ResponseBody
    List<JournalDTO> getAllJrnl(@RequestBody Login login){
        Users user = userService.findByLogin(login.getLogin());
        JournalDTO journalDTO = new JournalDTO();
        return journalDTO.getJournalDTOList(user.getJournal());
    }

    @PostMapping("/private/invitations")
    public @ResponseBody List<InvitationsDTO> getAllInvites(@RequestBody Login login){
        Users user = userService.findByLogin(login.getLogin());
        InvitationsDTO invitationsDTO = new InvitationsDTO();
        return invitationsDTO.getInvitationsDTOList(user.getIdInvitations());
    }

    @PostMapping("/private/accept")
    public void accept(@RequestBody LoginJournal loginJournal, HttpServletResponse response){
        try{
            Invitations invitations = userService.findByUserAndJournalAndAccept(loginJournal.getLogin(), loginJournal.getJournalName());
            String user = invitations.getIdUser().getLogin();
            String journal = invitations.getIdJournal().getName();
            String role = invitations.getIdRole().getRoleName();
            userService.setUserRoles(user, journal, role);
            userService.updateStatus(invitations);
            response.setStatus(HttpServletResponse.SC_OK);
        }catch(Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/private/decline")
    public void decline(@RequestBody LoginJournal loginJournal, HttpServletResponse response){
        try{
            Invitations invitations = userService.findByUserAndJournalAndAccept(loginJournal.getLogin(), loginJournal.getJournalName());
            userService.delete(invitations);
            response.setStatus(HttpServletResponse.SC_OK);
        }catch(Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}