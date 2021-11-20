package fafij.server.controllers;
import fafij.server.Repository.UserRolesService;
import fafij.server.entity.UserRoles;
import fafij.server.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class UserRolesController {
    @Autowired
    private UserRolesService userRolesService;

    @PostMapping("/addRole")
    public void addUser(@RequestBody UserRoles request, HttpServletResponse response){
        try{
            userRolesService.addUser(request);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateRole")
    public void updateRole(@RequestParam UserRoles user, @RequestParam UserRoles role, @RequestParam UserRoles jrnl, HttpServletResponse response){
        try{
            Users userEntity = userRolesService.findByUser(user).getIdUser();
            if (userEntity != null){
                if(jrnl.getIdJournal().equals(userEntity.getJournal())){
                    userRolesService.updateUserRoles(user, role);
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
}
