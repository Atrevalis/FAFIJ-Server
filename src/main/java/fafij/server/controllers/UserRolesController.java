package fafij.server.controllers;
import fafij.server.repository.JournalService;
import fafij.server.repository.RolesService;
import fafij.server.repository.UserRolesService;
import fafij.server.repository.UserService;
import fafij.server.entity.Journal;
import fafij.server.entity.Roles;
import fafij.server.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/private")
public class UserRolesController {
    @Autowired
    private UserRolesService userRolesService;
    @Autowired
    private UserService userService;
    @Autowired
    private JournalService journalService;
    @Autowired
    private RolesService roleService;

    @PostMapping("/updateRole")
    public void updateRole(@RequestParam String userLogin, @RequestParam String roleName, @RequestParam String journalName, HttpServletResponse response){
        try{
            Users user = userService.findByLogin(userLogin);
            Journal journal = journalService.findByName(journalName);
            Roles role = roleService.findByRoleName(roleName);
            if (user != null){
                if(journal.getId().equals(user.getJournal())){
                    //userRolesService.updateUserRoles(userLogin, roleName);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                }
                else {
                    response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }

        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addUser")
    public void addUser(@RequestParam String journalName, @RequestParam String userLogin, HttpServletResponse response){
        try{
            Users user = userService.findByLogin(userLogin);
            Journal journal = journalService.findByName(journalName);
            String defaultRole = "KID";
            Roles role = roleService.findByRoleName(defaultRole);
            //userRolesService.addUser(user, journal, role);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
