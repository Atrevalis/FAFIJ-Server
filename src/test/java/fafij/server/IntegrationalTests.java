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
import fafij.server.utils.Converters;
import fafij.server.utils.NoteDTOBean;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import fafij.server.repository.CategoryService;
import fafij.server.security.AuthRequest;
import fafij.server.security.AuthResponse;
import fafij.server.utils.Constants;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
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

    private String json;
    private Users user;
    private List<Journal> journals;
    private List<Roles> role;
    private List<Invitations> invitations;
    private AuthRequest authRequest;
    private AuthResponse authResponse;
    private MvcResult result;
    private JournalName journalName;
    private CreateJournal createJournal;
    private String requestJson;
    private ObjectWriter ow;
    private CategoryBody categoryBody;
    private AddNote addNote;
    private Gson g;
    private CategoryDTO category;
    private NoteDTO expectedNote;
    private NoteDTO requestNote;

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
        g = new Gson();
        addNote = new AddNote(Constants.date,Constants.summ, Constants.category, Constants.comment, Constants.journal);
        categoryBody = new CategoryBody(Constants.category,Constants.login,Constants.journal);
        createJournal = new CreateJournal(Constants.login,Constants.journal);
        journalName = new JournalName(Constants.journal);
        authRequest = new AuthRequest();
        authRequest.setLogin(Constants.login);
        authRequest.setPassword(Constants.password);
        user = new Users( Constants.login,  Constants.password, null, null, null);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("User registration, login and add journal with note")
    public void CreateCaseTest()throws Exception{
        requestJson=ow.writeValueAsString(user);

        //registration
        mvc.perform(post(Constants.registrationPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());

        //login
        result = mvc.perform(post(Constants.loginPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();
        json = result.getResponse().getContentAsString();
        json = json.substring(11, json.length()-2);//заменить на сериализацию
        authResponse = new AuthResponse(json);

        //createJournal
        requestJson=ow.writeValueAsString(createJournal);
        mvc.perform(post(Constants.privatePath+Constants.createJournalPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());

        //ask server about Node list
        requestJson=ow.writeValueAsString(journalName);
        result = mvc.perform(post(Constants.privatePath+Constants.listNotePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        json = result.getResponse().getContentAsString();
        Assert.assertEquals("Lists of Node are not equals","[]",json);

        //ask server about Category list
        result = mvc.perform(post(Constants.privatePath+Constants.listCategoryPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        json = result.getResponse().getContentAsString();
        Assert.assertEquals("Lists of Categories are not equals","[]",json);

        //create category
        requestJson=ow.writeValueAsString(categoryBody);
        mvc.perform(post(Constants.privatePath+Constants.addCategoryPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());

        //ask server again about Category list
        result = mvc.perform(post(Constants.privatePath+Constants.listCategoryPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        json = result.getResponse().getContentAsString();
        Assert.assertEquals("Lists of Categories are not equals","["+Constants.category+"]",json);

        //create Note
        requestJson=ow.writeValueAsString(addNote);
        mvc.perform(post(Constants.privatePath+Constants.addNotePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
        category.setName(Constants.category);
        expectedNote.setComment(Constants.comment);
        expectedNote.setDate(Constants.date);
        expectedNote.setIdCtgr(category);
        expectedNote.setSum(Constants.summ);
        //ask server again about Note list
        requestJson=ow.writeValueAsString(journalName);
        result = mvc.perform(post(Constants.privatePath+Constants.listNotePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();
        json = result.getResponse().getContentAsString();
        //json = "["+json.substring(2, json.length()-2)+"]";
        Type collectionType = new TypeToken<Collection<NoteDTOBean>>(){}.getType();
        List<NoteDTOBean> lcs = new Gson().fromJson( json , collectionType);
        //NoteDTOBean[] noteDTOBean = g.fromJson(json, NoteDTOBean[].class);
        requestNote.setComment(lcs.get(0).comment);
        requestNote.setDate(lcs.get(0).date);
        requestNote.setIdCtgr(lcs.get(0).category);
        requestNote.setSum(lcs.get(0).sum);
        expectedNote.setId(lcs.get(0).id);
        Assertions.assertEquals(expectedNote,requestNote, "Nodes are not equals");
    }
}