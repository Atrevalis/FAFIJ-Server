package fafij.server.controllers;
import fafij.server.dto.NoteDTO;
import fafij.server.repository.*;
import fafij.server.requestbodies.AddNote;
import fafij.server.requestbodies.DeleteNote;
import fafij.server.requestbodies.JournalName;
import fafij.server.requestbodies.UpdateNote;
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

    @PostMapping("/addNote")
    public void createNote(@RequestBody AddNote addNote, HttpServletResponse response){
        try{
            noteService.createNote(addNote.getDate(), addNote.getSum(), addNote.getCategory(), addNote.getComment(), addNote.getJournalName());
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteNote")
    public void deleteNote(@RequestBody DeleteNote deleteNote, HttpServletResponse response){
        try{
            Long idRole = noteService.findByUserAndJournal(deleteNote.getLogin(), deleteNote.getJournalName()).getIdRole().getId();
            if(idRole == 1 || idRole == 2){
                noteService.deleteNote(deleteNote.getIdNote());
                response.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/listNote")
    public @ResponseBody
    List<NoteDTO> listNote(@RequestBody JournalName journalName){
        NoteDTO noteDTO = new NoteDTO();
        return noteDTO.getNoteDTOList(noteService.findAllByIdJournalOrderByDate(journalName.getJournalName()));
    }

    @PostMapping("/updateNote")
    public void update(@RequestBody UpdateNote updateNote, HttpServletResponse response){
        try{
            Long idRole = noteService.findByUserAndJournal(updateNote.getLogin(), noteService.findById(updateNote.getId()).get().getIdJournal().getName()).getIdRole().getId();
            if(idRole == 1 || idRole == 2){
                noteService.updateNote(updateNote.getId(), updateNote.getDate(), updateNote.getSum(), updateNote.getCategory(), updateNote.getComment());
                response.setStatus(HttpServletResponse.SC_OK);
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }catch(Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
