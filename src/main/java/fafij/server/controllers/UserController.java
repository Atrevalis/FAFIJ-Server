package fafij.server.controllers;

import fafij.server.Repository.UserService;
import fafij.server.dto.JournalDTO;
import fafij.server.entity.Journal;
import fafij.server.entity.Roles;
import fafij.server.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @GetMapping(path="/private/userJournals")
    public @ResponseBody
    List<JournalDTO> getAllJrnl(@RequestParam String login){
        Users user = userService.findByLogin(login);
        JournalDTO journalDTO = new JournalDTO();
        return journalDTO.getJournalDTOList(user.getJournal());
    }
}