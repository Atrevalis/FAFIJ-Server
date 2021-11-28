package fafij.server.controllers;
import fafij.server.repository.JournalService;
import fafij.server.repository.RolesService;
import fafij.server.repository.UserRolesService;
import fafij.server.repository.UserService;
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

    @PostMapping("/createJournal")
    public void createJournal(@RequestBody CreateJournal createJournal, HttpServletResponse response){
        try {
            journalService.createJournal(createJournal.getJournalName());
            String role = "ADMIN";
            //userRolesService.addUser(login, journalName, role);
            userRolesService.setUserRoles(createJournal.getLogin(), createJournal.getJournalName(), role);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
