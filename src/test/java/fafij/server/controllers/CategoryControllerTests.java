package fafij.server.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import fafij.server.entity.Category;
import fafij.server.entity.Roles;
import fafij.server.entity.UserRoles;
import fafij.server.repository.CategoryService;
import fafij.server.requestbodies.CategoryBody;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CategoryControllerTests {

    private final String category = "Food";
    private final String login = "ilya";
    private final String journal = "Journal";
    private Category categoryClass = new Category();
    private UserRoles userRoles;
    private CategoryBody categoryBody;
    private MockMvc mvc;
    private final String path = "/private";
    private final String journalSat = "sat";
    private final String journalId = "69";

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    public void SetUp(){
        Roles roles = new Roles();
        userRoles = new UserRoles();
        userRoles.setIdRole(roles);
        categoryBody = new CategoryBody(category, login, journal);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    /*public void getFindAllNoteFromJournalTest()
            throws Exception {
        List<String> strings = Arrays.asList(journalSat);

        when(categoryService.findAllByIdJournal(journalId)).thenReturn(strings);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(journalSat);

        mvc.perform(post(path+"/listCategory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }*/

    @ParameterizedTest
    @ValueSource(longs = {Constants.AdminRole,Constants.AdultRole})
    public void CreateCategoryTest(long role) throws Exception {
        userRoles.getIdRole().setId(role);
        when(this.categoryService.findByUserAndJournal(categoryBody.getLogin(), categoryBody.getJournalName())).thenReturn(userRoles);
        when(this.categoryService.checkCategory(categoryBody.getCategory(), categoryBody.getJournalName())).thenReturn(true);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(categoryBody);

        mvc.perform(post(path+"/addCategory").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void CreateCategoryTestAsKid() throws Exception {
        userRoles.getIdRole().setId(Constants.KidRole);
        when(this.categoryService.findByUserAndJournal(categoryBody.getLogin(), categoryBody.getJournalName())).thenReturn(userRoles);
        when(this.categoryService.checkCategory(categoryBody.getCategory(), categoryBody.getJournalName())).thenReturn(true);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(categoryBody);

        mvc.perform(MockMvcRequestBuilders.post(path+"/addCategory").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void CreateCategoryTestWithError() throws Exception {
        Exception e = new RuntimeException();
        userRoles.getIdRole().setId(Constants.KidRole);
        when(this.categoryService.findByUserAndJournal(categoryBody.getLogin(), categoryBody.getJournalName())).thenThrow(e);
        when(this.categoryService.checkCategory(categoryBody.getCategory(),categoryBody.getJournalName())).thenThrow(e);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(categoryBody);

        mvc.perform(post(path+"/addCategory").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isInternalServerError());
    }

    @ParameterizedTest
    @ValueSource(longs = {Constants.AdminRole,Constants.AdultRole})
    public void DeleteCategoryTest(long role) throws Exception {
        categoryBody = new CategoryBody(category, login, journal);
        userRoles.getIdRole().setId(role);
        when(this.categoryService.findByUserAndJournal(categoryBody.getLogin(), categoryBody.getJournalName())).thenReturn(userRoles);
        when(categoryService.findByNameAndIdJournal(categoryBody.getCategory(), categoryBody.getJournalName())).thenReturn(categoryClass);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(categoryBody);
        this.mvc.perform(post(path+"/deleteCategory")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void DeleteCategoryTestAsKid() throws Exception {

        userRoles.getIdRole().setId(Constants.KidRole);
        when(this.categoryService.findByUserAndJournal(categoryBody.getLogin(), categoryBody.getJournalName())).thenReturn(userRoles);
        when(categoryService.findByNameAndIdJournal(category, journal)).thenReturn(categoryClass);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(categoryBody);
        mvc.perform(post(path+"/deleteCategory")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }

    @ParameterizedTest
    @ValueSource(longs = {Constants.AdminRole,Constants.AdultRole})
    public void DeleteCategoryTestWithError(long role) throws Exception {
        Exception e = new RuntimeException();
        userRoles.getIdRole().setId(role);
        when(this.categoryService.findByUserAndJournal(categoryBody.getLogin(), categoryBody.getJournalName())).thenThrow(e);
        when(categoryService.findByNameAndIdJournal(category, journal)).thenThrow(e);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(categoryBody);

        mvc.perform(post(path+"/deleteCategory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isInternalServerError());
    }
}