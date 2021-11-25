package fafij.server.controllers;

import fafij.server.Repository.UserService;
import fafij.server.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public void registration(@RequestBody Users request, HttpServletResponse response) {
        try {
            userService.createUsers(request);
            response.setStatus(HttpServletResponse.SC_CREATED);
       }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/private/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Users> getAll() {
        return this.userService.findAll();
    }
}