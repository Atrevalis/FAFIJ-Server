package fafij.server.unit.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import fafij.server.dto.NoteDTO;
import fafij.server.entity.*;
import fafij.server.repository.NoteService;
import fafij.server.requestbodies.*;
import fafij.server.utils.Constants;
import fafij.server.utils.Converters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class NoteControllerTests {

    private final String path = "/private";
    private final String addPath = "/addNote";
    private final String updatePath = "/updateNote";
    private final String deletePath = "/deleteNote";
    private AddNote addNote;
    private DeleteNote deleteNote;
    private JournalName journalName;
    private final String date = "06.12.2022 19:33:00";
    private final long sum = 2000;
    private final String nameCategory = "Food";
    private Category category;
    private final String comment = "comment";
    private MockMvc mvc;
    private final long idNote = 1;
    private final String login = "ilya";
    private UserRoles userRoles;
    private final String nameJournal = "Journal";
    private Journal journal;
    private final String listPath = "/listNote";
    private List<Note> listNote;
    private final int index = 1;
    private Note note;
    private NoteDTO noteDTO;
    private UpdateNote updateNote;
    private Optional<Note> optionalNote;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    private NoteService noteService;

    @BeforeEach
    public void setUp() throws Exception {
        journalName = new JournalName(nameJournal);

        category = new Category();
        journal = new Journal();
        note = new Note(idNote,Converters.stringToDate(date),sum,category, journal,comment);
        updateNote = new UpdateNote(idNote,date,sum, nameCategory,comment,login);
        Roles roles = new Roles();
        userRoles = new UserRoles();
        userRoles.setIdRole(roles);
        deleteNote = new DeleteNote(idNote, login, nameJournal);
        addNote = new AddNote( date, sum, nameCategory, comment);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createNoteTest() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(addNote);

        mvc.perform(post(path+addPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
    }

 /*   @Test
    void createNoteTestException() throws Exception{
        when(noteService.createNote(addNote.getDate(), addNote.getSum(), addNote.getCategory(), addNote.getComment(), addNote.getJournalName()))

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(null);
        mvc.perform(post(path+addPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isInternalServerError());
    }*/

   @ParameterizedTest
   @ValueSource(longs = {Constants.AdminRole,Constants.AdultRole})
    void deleteNoteTest(long role) throws Exception{
        userRoles.getIdRole().setId(role);
        when(noteService.findByUserAndJournal(deleteNote.getLogin(), deleteNote.getJournalName())).thenReturn(userRoles);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(deleteNote);

        mvc.perform(post(path+deletePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteNoteTestNotAcceptable()throws Exception{
        userRoles.getIdRole().setId(Constants.KidRole);
        when(noteService.findByUserAndJournal(deleteNote.getLogin(), deleteNote.getJournalName())).thenReturn(userRoles);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(deleteNote);

        mvc.perform(post(path+deletePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void deleteNoteTestError()throws Exception{
        Exception e = new RuntimeException();
        userRoles.getIdRole().setId(Constants.AdminRole);
        when(noteService.findByUserAndJournal(deleteNote.getLogin(), deleteNote.getJournalName())).thenThrow(e);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(deleteNote);

        mvc.perform(post(path+deletePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void listNoteTest() throws Exception{
        listNote = new ArrayList<>();
        listNote.add(note);
        when(noteService.findAllByIdJournalOrderByDate(journalName.getJournalName())).thenReturn((List<Note>) listNote);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(journalName);

        mvc.perform(post(path+listPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect((ResultMatcher) noteDTO.getNoteDTOList(listNote));
    }

    @Test
    void updateTest() throws Exception{
        when(noteService.findById(updateNote.getId())).thenReturn(optionalNote);
        when(noteService.findByUserAndJournal(updateNote.getLogin(), noteService.findById(updateNote.getId()).get().getIdJournal().getName())).thenReturn(userRoles);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(updateNote);

        mvc.perform(post(path+updatePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }
}