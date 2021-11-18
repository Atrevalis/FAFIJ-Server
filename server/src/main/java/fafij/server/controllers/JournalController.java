package fafij.server.controllers;
import fafij.server.Repository.JournalService;
import fafij.server.Repository.UserService;
import fafij.server.entity.Journal;
import fafij.server.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class JournalController {
    @Autowired
    private JournalService journalService;
    private UserService userService;

    @PostMapping("/createJournal")
    public void createJournal(@RequestBody Journal request, HttpServletResponse response){
        try {
            journalService.createJournal(request);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/listJournal")
    public @ResponseBody
    List<Journal> findAll(){
        return this.journalService.findAll();
    }
}
