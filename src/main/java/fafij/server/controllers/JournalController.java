package fafij.server.controllers;
import fafij.server.repository.*;
import fafij.server.requestbodies.AddUser;
import fafij.server.requestbodies.CreateJournal;
import fafij.server.requestbodies.LoginJournal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/private")
public class JournalController {
    @Autowired
    private JournalService journalService;

    @PostMapping("/createJournal")
    public void createJournal(@RequestBody CreateJournal createJournal, HttpServletResponse response){
        try {
            if(journalService.existsByNAme(createJournal.getJournalName())){
                journalService.createJournal(createJournal.getJournalName());
                String role = "ADMIN";
                journalService.setUserRoles(createJournal.getLogin(), createJournal.getJournalName(), role);
                response.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody AddUser addUser, HttpServletResponse response){
        try {
            if(journalService.existsUser(addUser.getLogin())){
                if(journalService.checkUserRole(addUser.getLogin(), addUser.getJournalName())){
                    if(journalService.checkUserInvite(addUser.getLogin(), addUser.getJournalName())){
                        if(journalService.checkUserDecline(addUser.getLogin(), addUser.getJournalName())){
                            Long idRole = journalService.findByUserAndJournal(addUser.getAdmin(), addUser.getJournalName()).getIdRole().getId();
                            if(idRole == 1){
                                journalService.addInvitation(addUser.getLogin(), addUser.getJournalName(), addUser.getRole());
                                response.setStatus(HttpServletResponse.SC_CREATED);
                            }else{
                                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                            }
                        }else{
                            response.setStatus(HttpServletResponse.SC_SEE_OTHER);
                        }
                    }else{
                        response.setStatus(HttpServletResponse.SC_SEE_OTHER);
                    }
                }else{
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                }
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/userRole")
    public @ResponseBody String userRole(@RequestBody LoginJournal loginJournal){
        return journalService.findByUserAndJournal(loginJournal.getLogin(), loginJournal.getJournalName()).getIdRole().getId().toString();
    }
}
