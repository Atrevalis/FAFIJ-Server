package fafij.server;

import fafij.server.controllers.AuthenticationController;
import fafij.server.entity.Category;
import fafij.server.entity.Roles;
import fafij.server.entity.UserRoles;
import fafij.server.repository.CategoryService;
import fafij.server.repository.UserRolesService;
import fafij.server.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
    @WebMvcTest(AuthenticationController.class)
    @SpringBootTest
    @AutoConfigureMockMvc
    public class CategoryControllerTests {

    final String category = "Food";
    final String login = "ilya";
    final String journalName = "ilyaJournal";
    final Long id = 1L;
    Category categoryClass;
    UserRoles userRoles;
    Roles roles;

        @Autowired
        private MockMvc mvc;

        @MockBean
        private CategoryService categoryService;

        @MockBean
        UserRolesService userRolesService;

        @BeforeEach
        public void SetUp(){
            roles = new Roles();
            userRoles = new UserRoles();
            userRoles.setIdRole(roles);
        }

    /*@Test
    public void getFindAllNoteFromJournalTest()
    throws Exception {

        String journalSat = "sat";

        List<String> strings = Arrays.asList(journalSat);

        given(categoryService.findAllByIdJournal("0")).willReturn(strings);

      *//* mvc.perform(get("/api/employees")
                .contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(alex.getName()))); *//*
        }

    @ParameterizedTest
    @ValueSource(longs = {Constants.AdminRole,Constants.AdultRole})
    public void CreateCategoryTest(long role) throws Exception {
        userRoles.getIdRole().setId(role);
        given(userRolesService.findByUserAndJournal(login, journalName)).willReturn(userRoles);

        mvc.perform(get("/addCategory")
                .contentType(MediaType.ALL))
                .andExpect(status().isCreated());
    }

    @Test
    public void CreateCategoryTestAsKid() throws Exception {

        userRoles.getIdRole().setId(Constants.KidRole);
        given(userRolesService.findByUserAndJournal(login, journalName)).willReturn(userRoles);

        mvc.perform(get("/addCategory")
                .contentType(MediaType.ALL))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void CreateCategoryTestWithError() throws Exception {

        userRoles.getIdRole().setId(Constants.KidRole);
        given(userRolesService.findByUserAndJournal(login, journalName)).willThrow(new IllegalStateException("Error occurred"));

        mvc.perform(get("/addCategory")
                .contentType(MediaType.ALL))
                .andExpect(status().isInternalServerError());
    }

    @ParameterizedTest
    @ValueSource(longs = {Constants.AdminRole,Constants.AdultRole})
    public void DeleteCategoryTest(long role) throws Exception {
        userRoles.getIdRole().setId(role);
        given(userRolesService.findByUserAndJournal(login, journalName)).willReturn(userRoles);
        given(categoryService.findByNameAndIdJournal(category, journalName)).willReturn(categoryClass);

        mvc.perform(post("/deleteCategory")
                .contentType(MediaType.ALL))
                .andExpect(status().isCreated());
    }

    @Test
    public void DeleteCategoryTestAsKid() throws Exception {

        userRoles.getIdRole().setId(Constants.KidRole);
        given(userRolesService.findByUserAndJournal(login, journalName)).willReturn(userRoles);
        given(categoryService.findByNameAndIdJournal(category, journalName)).willReturn(categoryClass);

        mvc.perform(post("/deleteCategory")
                .contentType(MediaType.ALL))
                .andExpect(status().isCreated());
    }

    @Test
    public void DeleteCategoryTestWithError() throws Exception {

        userRoles.getIdRole().setId(Constants.KidRole);
        given(userRolesService.findByUserAndJournal(login, journalName)).willThrow(new IllegalStateException("Error occurred"));
        given(categoryService.findByNameAndIdJournal(category, journalName)).willThrow(new IllegalStateException("Error occurred"));

        mvc.perform(post("/deleteCategory")
                .contentType(MediaType.ALL))
                .andExpect(status().isCreated());
    }*/
}