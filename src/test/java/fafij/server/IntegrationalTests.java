package fafij.server;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fafij.server.dto.CategoryDTO;
import fafij.server.dto.NoteDTO;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@TestPropertySource("/applicationTest.properties")
//@Sql(value = {"runDumpDB.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(value = {"clearDB.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@AutoConfigureMockMvc
public class IntegrationalTests {

    private MockMvc mvc;

    private Login login;
    private String json;
    private Users user;
    private List<Invitations> invitations;
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

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    public void SetUp(){
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
        requestJson=ow.writeValueAsString(user);

        //registration
        mvc.perform(post(Constants.Path.registrationPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());

        //login
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
        Assertions.assertEquals("["+Constants.User.category+"]",json, "Lists of Categories are not equals");

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
        Type collectionType = new TypeToken<Collection<NoteDTOBean>>(){}.getType();
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
        deleteNote = new DeleteNote(Constants.UserDB.idNote, Constants.UserDB.login,Constants.UserDB.journal);
        user = new Users( Constants.UserDB.login,  Constants.UserDB.password, null, null, null);
        login = new Login(Constants.UserDB.login);
        journalName = new JournalName(Constants.UserDB.journal);
        //login
        requestJson=ow.writeValueAsString(user);
        mvc.perform(post(Constants.Path.loginPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
      /*  //ask server about Journal list
        requestJson=ow.writeValueAsString(login);
        result = mvc.performs(post(Constants.Path.privatePath+Constants.Path.userJournals)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        json = result.getResponse().getContentAsString();
       */
    //[{"journalName":"BatalinJournal","logins":[{"login":"AlexanderBatalin"}]}]
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
        Type collectionType = new TypeToken<Collection<NoteDTOBean>>(){}.getType();
        List<NoteDTOBean> lcs = new Gson().fromJson( json , collectionType);
        boolean clone = false;
        for(int i = 0; i<lcs.size(); i++) {
            clone = Constants.UserDB.comment.equals(lcs.get(i).comment) &&
                    Constants.UserDB.date == lcs.get(i).date &&
                    Constants.UserDB.category.equals(lcs.get(i).category) &&
                    Constants.UserDB.sum == lcs.get(i).sum &&
                    Constants.UserDB.idNote == lcs.get(i).id;
        }
        Assertions.assertFalse(clone, "Note is not deleted");

        //delete categories
        requestJson=ow.writeValueAsString(journalName);
        result = mvc.perform(post(Constants.Path.privatePath+Constants.Path.listNotePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        json = result.getResponse().getContentAsString();
        Type collectionType = new TypeToken<Collection<NoteDTOBean>>(){}.getType();
        List<NoteDTOBean> lcs = new Gson().fromJson( json , collectionType);
        //Assertions.assertEquals(expectedNote,requestNote, "Notes are not equals");

    }
}