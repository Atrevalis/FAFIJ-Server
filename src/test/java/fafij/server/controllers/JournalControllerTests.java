package fafij.server.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import fafij.server.entity.Roles;
import fafij.server.entity.UserRoles;
import fafij.server.repository.JournalService;
import fafij.server.requestbodies.AddUser;
import fafij.server.requestbodies.CreateJournal;
import fafij.server.requestbodies.LoginJournal;
import fafij.server.utils.Constants;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class JournalControllerTests {

    private final String login = "ilya";
    private final String journal = "Journal";
    private final String path = "/private";
    private final String createPath ="/createJournal";
    private final String addUserPath = "/addUser";
    private MockMvc mvc;
    private UserRoles userRoles;
    private LoginJournal loginJournal;
    private CreateJournal createJournal;
    private AddUser addUser;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    private JournalService journalService;

    @BeforeEach
    public void setUp(){
        addUser = new AddUser(journal, login, Constants.AdminRole+"");
        createJournal = new CreateJournal(login, journal);
        loginJournal = new LoginJournal(login, journal);
        Roles roles = new Roles();
        userRoles = new UserRoles();
        userRoles.setIdRole(roles);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createJournalTest() throws Exception{
        when(journalService.existsByNAme(createJournal.getJournalName())).thenReturn(true);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(createJournal);

        mvc.perform(post(path+createPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void createJournalConflictTest() throws Exception{
        when(journalService.existsByNAme(createJournal.getJournalName())).thenReturn(false);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(createJournal);

        mvc.perform(post(path+createPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isConflict());
    }

    @Test
    public void createJournalErrorTest() throws Exception{
        Exception e = new RuntimeException();
        when(journalService.existsByNAme(createJournal.getJournalName())).thenThrow(e);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(createJournal);

        mvc.perform(post(path+createPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void addUserTest() throws Exception{
        userRoles.getIdRole().setId(Constants.AdminRole);
        when(journalService.existsUser(addUser.getLogin())).thenReturn(true);
        when(journalService.checkUserRole(addUser.getLogin(), addUser.getJournalName())).thenReturn(true);
        when(journalService.checkUserInvite(addUser.getLogin(), addUser.getJournalName())).thenReturn(true);
        when(journalService.checkUserDecline(addUser.getLogin(), addUser.getJournalName())).thenReturn(true);
        when(journalService.findByUserAndJournal(addUser.getAdmin(), addUser.getJournalName())).thenReturn(userRoles);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(addUser);

        mvc.perform(post(path+addUserPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void addUserTestConflict() throws Exception{
        userRoles.getIdRole().setId(Constants.AdminRole);
        when(journalService.existsUser(addUser.getLogin())).thenReturn(true);
        when(journalService.checkUserRole(addUser.getLogin(), addUser.getJournalName())).thenReturn(false);
        when(journalService.findByUserAndJournal(addUser.getAdmin(), addUser.getJournalName())).thenReturn(userRoles);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(addUser);

        mvc.perform(post(path+addUserPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isConflict());
    }
    @ParameterizedTest
    @ValueSource(longs = {Constants.KidRole,Constants.AdultRole})
    public void addUserTestNotAcceptable(long role) throws Exception{
        userRoles.getIdRole().setId(role);
        when(journalService.existsUser(addUser.getLogin())).thenReturn(true);
        when(journalService.checkUserRole(addUser.getLogin(), addUser.getJournalName())).thenReturn(true);
        when(journalService.checkUserInvite(addUser.getLogin(), addUser.getJournalName())).thenReturn(true);
        when(journalService.checkUserDecline(addUser.getLogin(), addUser.getJournalName())).thenReturn(true);
        when(journalService.findByUserAndJournal(addUser.getAdmin(), addUser.getJournalName())).thenReturn(userRoles);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(addUser);

        mvc.perform(post(path+addUserPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isNotAcceptable());
    }
    @Test
    public void addUserTestWithoutInvite() throws Exception{
        userRoles.getIdRole().setId(Constants.AdminRole);
        when(journalService.existsUser(addUser.getLogin())).thenReturn(true);
        when(journalService.checkUserRole(addUser.getLogin(), addUser.getJournalName())).thenReturn(true);
        when(journalService.checkUserInvite(addUser.getLogin(), addUser.getJournalName())).thenReturn(false);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(addUser);

        mvc.perform(post(path+addUserPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isSeeOther());
    }

    @Test
    public void addUserTestDecline() throws Exception{
        userRoles.getIdRole().setId(Constants.AdminRole);
        when(journalService.existsUser(addUser.getLogin())).thenReturn(true);
        when(journalService.checkUserRole(addUser.getLogin(), addUser.getJournalName())).thenReturn(true);
        when(journalService.checkUserInvite(addUser.getLogin(), addUser.getJournalName())).thenReturn(true);
        when(journalService.checkUserDecline(addUser.getLogin(), addUser.getJournalName())).thenReturn(false);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(addUser);

        mvc.perform(post(path+addUserPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isSeeOther());
    }
    @Test
    public void addUserTestNotFound() throws Exception{
        userRoles.getIdRole().setId(Constants.AdminRole);
        when(journalService.existsUser(addUser.getLogin())).thenReturn(false);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(addUser);

        mvc.perform(post(path+addUserPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addUserTestError() throws Exception{
        Exception e = new RuntimeException();
        userRoles.getIdRole().setId(Constants.AdminRole);
        when(journalService.existsUser(addUser.getLogin())).thenThrow(e);
        when(journalService.checkUserRole(addUser.getLogin(), addUser.getJournalName())).thenThrow(e);
        when(journalService.checkUserInvite(addUser.getLogin(), addUser.getJournalName())).thenThrow(e);
        when(journalService.findByUserAndJournal(addUser.getAdmin(), addUser.getJournalName())).thenThrow(e);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(addUser);

        mvc.perform(post(path+addUserPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isInternalServerError());
    }


    @ParameterizedTest
    @ValueSource(longs = {Constants.AdminRole,Constants.AdultRole, Constants.KidRole})
    void userRoleTest(long role) throws Exception{
        userRoles.getIdRole().setId(role);
        when(journalService.findByUserAndJournal(loginJournal.getLogin(), loginJournal.getJournalName())).thenReturn(userRoles);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(userRoles.getIdRole().getId());

        mvc.perform(post(path+"/userRole")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));
    }

}
