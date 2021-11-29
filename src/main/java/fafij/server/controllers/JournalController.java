package fafij.server.controllers;
import fafij.server.repository.*;
import fafij.server.requestbodies.AddUser;
import fafij.server.requestbodies.CreateJournal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/private")
public class JournalController {
    @Autowired
    private JournalService journalService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRolesService userRolesService;
    @Autowired
    private RolesService roleService;
    @Autowired
    private InvitationsService invitationsService;

    @PostMapping("/createJournal")
    public void createJournal(@RequestBody CreateJournal createJournal, HttpServletResponse response){
        try {
            journalService.createJournal(createJournal.getJournalName());
            String role = "ADMIN";
            userRolesService.setUserRoles(createJournal.getLogin(), createJournal.getJournalName(), role);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody AddUser addUser, HttpServletResponse response){
        try {
            Long id = roleService.findByRoleName(addUser.getRole()).getId();
            if(id == 2 || id == 3){
                invitationsService.addInvitation(addUser.getLogin(), addUser.getJournalName(), addUser.getRole());
                response.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
