package fafij.server.controllers;
import fafij.server.Repository.NoteService;
import fafij.server.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class NoteController {
    @Autowired
    private NoteService noteService;

    @PostMapping("/createNote")
    public void createNote(@RequestBody Note request, HttpServletResponse response){
        try{
            noteService.createNote(request);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteNote")
    public void deleteNote(@RequestBody Note request, HttpServletResponse response){
        try{
            noteService.deleteNote(request);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteNoteAdmin")
    public void deleteNoteAdmin(@RequestParam Note request, @RequestParam UserRoles admin, HttpServletResponse response){
        try{
            Long idRole = admin.getIdRole().getId();
            if(idRole == 1){
                noteService.deleteNote(request);
                response.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteNoteAdult")
    public void deleteNoteAdult(@RequestParam Note request, @RequestParam UserRoles adult, HttpServletResponse response){
        try{
            Long idRole = adult.getIdRole().getId();
            if(idRole == 2){
                noteService.deleteNote(request);
                response.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
