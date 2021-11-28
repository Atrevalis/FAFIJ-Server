package fafij.server.controllers;

import fafij.server.repository.UserService;
import fafij.server.dto.JournalDTO;
import fafij.server.entity.Users;
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

    @GetMapping(path="/private/userJournals")
    public @ResponseBody
    List<JournalDTO> getAllJrnl(@RequestBody Login login){
        Users user = userService.findByLogin(login.getLogin());
        JournalDTO journalDTO = new JournalDTO();
        return journalDTO.getJournalDTOList(user.getJournal());
    }
}