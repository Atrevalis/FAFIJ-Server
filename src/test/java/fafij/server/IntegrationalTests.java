package fafij.server;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import fafij.server.entity.*;
import org.junit.jupiter.api.DisplayName;
import fafij.server.repository.CategoryService;
import fafij.server.requestbodies.CategoryBody;
import fafij.server.requestbodies.CreateJournal;
import fafij.server.requestbodies.JournalName;
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

import java.util.Arrays;
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

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    public void SetUp(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ow = mapper.writer().withDefaultPrettyPrinter();

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
    }
}