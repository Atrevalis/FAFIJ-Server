package fafij.server.controllers;
import fafij.server.repository.NoteService;
import fafij.server.repository.UserRolesService;
import fafij.server.repository.UserService;
import fafij.server.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/private")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRolesService userRolesService;

    @PostMapping("/addNote")
    public void createNote(@RequestParam String date, @RequestParam Long sum, @RequestParam String category, @RequestParam String comment, @RequestParam String journalName, HttpServletResponse response){
        try{
            noteService.createNote(date, sum, category, comment, journalName);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteNote")
    public void deleteNote(@RequestParam Long idNote, @RequestParam String login, @RequestParam String journalName, HttpServletResponse response){
        try{
            Long idRole = userRolesService.findByUserAndJournal(login, journalName).getIdRole().getId();
            if(idRole == 1 || idRole == 2){
                noteService.deleteNote(idNote);
                response.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listNote")
    public @ResponseBody
    List<Note> listNote(String journalName){
        return this.noteService.findAllByJournal(journalName);
    }
}
