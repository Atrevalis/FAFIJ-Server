package fafij.server.controllers;
import fafij.server.Repository.JournalService;
import fafij.server.Repository.RolesService;
import fafij.server.Repository.UserRolesService;
import fafij.server.Repository.UserService;
import fafij.server.entity.Journal;
import fafij.server.entity.Roles;
import fafij.server.entity.UserRoles;
import fafij.server.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void createJournal(@RequestParam String login, @RequestParam String journalName, HttpServletResponse response){
        try {
            journalService.createJournal(journalName);
            String role = "ADMIN";
            //userRolesService.addUser(login, journalName, role);
            userRolesService.setUserRoles(login, journalName, role);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
