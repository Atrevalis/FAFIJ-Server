package fafij.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fafij.server.dto.*;
import fafij.server.entity.*;
import fafij.server.requestbodies.*;
import fafij.server.utils.NoteDTOBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import fafij.server.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@TestPropertySource("/applicationTest.properties")
@AutoConfigureMockMvc
public class IntegrationalTests {

    private MockMvc mvc;

    private ArrayList<Roles> roles;
    private ArrayList<UsersDTO> users;
    private ArrayList<Note> idNote;
    private JournalDTO journalDTO;
    private InvitationsDTO invitationsDTO;
    private boolean clone;
    private Login login;
    private String json;
    private Users user;
    private Users userDB;
    private ArrayList<InvitationsDTO> invitations;
    private MvcResult result;
    private JournalName journalName;
    private CreateJournal createJournal;
    private String requestJson;
    private ObjectWriter ow;
    private CategoryBody categoryBody;
    private AddNote addNote;
    private CategoryDTO category;
    private NoteDTO expectedNote;
    private NoteDTO requestNote;
    private DeleteNote deleteNote;
    private Type collectionType;
    private List<NoteDTOBean> lnb;
    private List<Category> categories;
    private UpdateNote updateNote;

    @Autowired
    WebApplicationContext webApplicationContext;


    @BeforeEach
    public void SetUp(){
        requestNote = new NoteDTO();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ow = mapper.writer().withDefaultPrettyPrinter();
        requestNote = new NoteDTO();
        expectedNote = new NoteDTO();
        category = new CategoryDTO();
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("User registration, login and add journal with note")
    public void createCaseTest()throws Exception{
        user = new Users( Constants.User.login,  Constants.User.password, null, null, null);
        addNote = new AddNote(Constants.User.date,Constants.User.sum, Constants.User.category, Constants.User.comment, Constants.User.journal);
        categoryBody = new CategoryBody(Constants.User.category,Constants.User.login,Constants.User.journal);
        createJournal = new CreateJournal(Constants.User.login,Constants.User.journal);
        journalName = new JournalName(Constants.User.journal);
        login = new Login(Constants.User.login);


        //registration
        requestJson=ow.writeValueAsString(user);
        mvc.perform(post(Constants.Path.registrationPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());

        //login
        requestJson=ow.writeValueAsString(user);
        mvc.perform(post(Constants.Path.loginPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        //createJournal
        requestJson=ow.writeValueAsString(createJournal);
        mvc.perform(post(Constants.Path.privatePath+Constants.Path.createJournalPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
        //ask server about Node list
        requestJson=ow.writeValueAsString(journalName);
        result = mvc.perform(post(Constants.Path.privatePath+Constants.Path.listNotePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        json = result.getResponse().getContentAsString();
        Assertions.assertEquals("[]",json, "Lists of Node are not equals");

        //ask server about Category list
        result = mvc.perform(post(Constants.Path.privatePath+Constants.Path.listCategoryPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        json = result.getResponse().getContentAsString();
        Assertions.assertEquals("[]",json, "Lists of Categories are not equals");

        //create category
        requestJson=ow.writeValueAsString(categoryBody);
        mvc.perform(post(Constants.Path.privatePath+Constants.Path.addCategoryPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());

        //ask server again about Category list
        result = mvc.perform(post(Constants.Path.privatePath+Constants.Path.listCategoryPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        json = result.getResponse().getContentAsString();
        Assertions.assertEquals("[{"+"\"name\":"+"\""+Constants.User.category+"\""+"}]",json, "Lists of Categories are not equals");

        //create Note
        requestJson=ow.writeValueAsString(addNote);
        mvc.perform(post(Constants.Path.privatePath+Constants.Path.addNotePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
        category.setName(Constants.User.category);
        expectedNote.setComment(Constants.User.comment);
        expectedNote.setDate(Constants.User.date);
        expectedNote.setIdCtgr(category);
        expectedNote.setSum(Constants.User.sum);

        //ask server again about Note list
        requestJson=ow.writeValueAsString(journalName);
        result = mvc.perform(post(Constants.Path.privatePath+Constants.Path.listNotePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        json = result.getResponse().getContentAsString();
        collectionType = new TypeToken<Collection<NoteDTOBean>>(){}.getType();
        List<NoteDTOBean> lcs = new Gson().fromJson( json , collectionType);
        requestNote.setComment(lcs.get(0).comment);
        requestNote.setDate(lcs.get(0).date);
        requestNote.setIdCtgr(lcs.get(0).category);
        requestNote.setSum(lcs.get(0).sum);
        expectedNote.setId(lcs.get(0).id);
        Assertions.assertEquals(expectedNote,requestNote, "Nodes are not equals");
    }

    @Test
    @DisplayName("Exist User login and delete note and category and journal")
    public void deleteCaseTest()throws Exception {
        journalName = new JournalName(Constants.UserDB.journal);
        categoryBody = new CategoryBody(Constants.UserDB.category,Constants.UserDB.login,Constants.UserDB.journal);
        category.setName(Constants.UserDB.category);
        deleteNote = new DeleteNote(Constants.UserDB.idNote, Constants.UserDB.login,Constants.UserDB.journal);
        userDB = new Users( Constants.UserDB.login,  Constants.UserDB.password, null, null, null);
        login = new Login(Constants.UserDB.login);
        journalName = new JournalName(Constants.UserDB.journal);
        expectedNote.setComment(Constants.UserDB.comment);
        expectedNote.setDate(Constants.UserDB.date);
        expectedNote.setIdCtgr(category);
        expectedNote.setSum(Constants.UserDB.sum);

        //login
        requestJson=ow.writeValueAsString(userDB);
        mvc.perform(post(Constants.Path.loginPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
        //delete note
        requestJson=ow.writeValueAsString(deleteNote);
        mvc.perform(post(Constants.Path.privatePath+Constants.Path.deleteNotePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
        //check list notes
        requestJson=ow.writeValueAsString(journalName);
        result = mvc.perform(post(Constants.Path.privatePath+Constants.Path.listNotePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        json = result.getResponse().getContentAsString();
        collectionType = new TypeToken<Collection<NoteDTOBean>>(){}.getType();
        lnb = new Gson().fromJson( json , collectionType);
        clone = false;
        for (NoteDTOBean noteDTOBean : lnb) {
            requestNote.setComment(noteDTOBean.comment);
            requestNote.setDate(noteDTOBean.date);
            requestNote.setIdCtgr(noteDTOBean.category);
            requestNote.setSum(noteDTOBean.sum);
            expectedNote.setId(noteDTOBean.id);
            clone = requestNote.equals(expectedNote);
            if (clone) {break;}
        }
        Assertions.assertFalse(clone, "Note is not deleted");

        //delete categories
        requestJson=ow.writeValueAsString(categoryBody);
        mvc.perform(post(Constants.Path.privatePath+Constants.Path.deleteCategoryPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());

        //Check categories list
        requestJson=ow.writeValueAsString(journalName);
        result = mvc.perform(post(Constants.Path.privatePath+Constants.Path.listCategoryPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        json = result.getResponse().getContentAsString();
        collectionType = new TypeToken<List<Category>>(){}.getType();
        categories = new Gson().fromJson( json , collectionType);
        clone = false;
        for (Category category : categories) {
           clone = category.equals(Constants.UserDB.category);
            if (clone) {break;}
        }
        Assertions.assertFalse(clone, "Category is not deleted");
    }

    @Test
    @DisplayName("Update note")
    public void UpdateJournalTest() throws Exception {
        category.setName(Constants.NewNote.category);
        expectedNote.setComment(Constants.NewNote.comment);
        expectedNote.setDate(Constants.NewNote.date);
        expectedNote.setIdCtgr(category);
        expectedNote.setSum(Constants.NewNote.sum);
        expectedNote.setId(Constants.UserDB.idNote);
        journalName = new JournalName(Constants.UserDB.journal);
        user = new Users( Constants.User.login,  Constants.User.password, null, null, null);
        userDB = new Users( Constants.UserDB.login,  Constants.UserDB.password, null, null, null);
        updateNote = new UpdateNote(Constants.UserDB.idNote, Constants.NewNote.date, Constants.NewNote.sum, Constants.NewNote.category, Constants.NewNote.comment, Constants.UserDB.login);

        //registration member
        requestJson=ow.writeValueAsString(user);
        mvc.perform(post(Constants.Path.registrationPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());

        //login journal host
        requestJson=ow.writeValueAsString(userDB);
        mvc.perform(post(Constants.Path.loginPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        //update note
        requestJson=ow.writeValueAsString(updateNote);
        mvc.perform(post(Constants.Path.privatePath+Constants.Path.updateNotePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        //check note
        requestJson=ow.writeValueAsString(journalName);
        result = mvc.perform(post(Constants.Path.privatePath+Constants.Path.listNotePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        json = result.getResponse().getContentAsString();
        collectionType = new TypeToken<Collection<NoteDTOBean>>(){}.getType();
        lnb = new Gson().fromJson( json , collectionType);
        for (NoteDTOBean noteDTOBean : lnb) {
            if(noteDTOBean.id == Constants.UserDB.idNote){
                requestNote.setComment(noteDTOBean.comment);
                requestNote.setDate(noteDTOBean.date);
                requestNote.setIdCtgr(noteDTOBean.category);
                requestNote.setSum(noteDTOBean.sum);
                requestNote.setId(Constants.UserDB.idNote);
            }
        }
        Assertions.assertEquals(expectedNote,requestNote, "Note is not updated");
    }
}