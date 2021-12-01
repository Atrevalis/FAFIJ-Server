package fafij.server;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import fafij.server.entity.Category;
import fafij.server.entity.Roles;
import fafij.server.entity.UserRoles;
import fafij.server.repository.CategoryService;
import fafij.server.repository.UserRolesService;
import fafij.server.requestbodies.CategoryBody;
import fafij.server.requestbodies.JournalName;
import fafij.server.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
    public class CategoryControllerTests {

    private final String category = "Food";
    private final String login = "ilya";
    private final String journal = "Journal";
    private Category categoryClass;
    private UserRoles userRoles;
    private CategoryBody categoryBody;
    private JournalName journalName;
    private MockMvc mvc;
    private String path = "/private";

        @Autowired
        WebApplicationContext webApplicationContext;

        @MockBean
        @Qualifier("category")
        private CategoryService categoryService;

        @MockBean
        @Qualifier("userRoles")
        private UserRolesService userRolesService;

        @BeforeEach
        public void SetUp(){
            Roles roles = new Roles();
            userRoles = new UserRoles();
            userRoles.setIdRole(roles);
            categoryBody = new CategoryBody(category, login, journal);
            mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }

   /*@Test
    public void getFindAllNoteFromJournalTest()
    throws Exception {

        String journalSat = "sat";

        List<String> strings = Arrays.asList(journalSat);

        when(categoryService.findAllByIdJournal("69")).thenReturn(strings);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString();

        mvc.perform(post(path+"/listCategory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }*/

    @ParameterizedTest
    @ValueSource(longs = {Constants.AdminRole,Constants.AdultRole})
    public void CreateCategoryTest(long role) throws Exception {
        userRoles.getIdRole().setId(role);
        when(userRolesService.findByUserAndJournal(categoryBody.getLogin(), categoryBody.getJournalName())).thenReturn(userRoles);
        when(categoryService.checkCategory(categoryBody.getCategory(), categoryBody.getJournalName())).thenReturn(true);
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
        given(userRolesService.findByUserAndJournal(login, journal)).willReturn(userRoles);

        mvc.perform(post("/addCategory")
                .contentType(MediaType.ALL))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void CreateCategoryTestWithError() throws Exception {

        userRoles.getIdRole().setId(Constants.KidRole);
        when(categoryService.checkCategory(categoryBody.getCategory(),categoryBody.getJournalName())).thenThrow(new Exception("Error occurred"));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(categoryBody);

        mvc.perform(post("/private/addCategory").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isInternalServerError());
    }

    @ParameterizedTest
    @ValueSource(longs = {Constants.AdminRole,Constants.AdultRole})
    public void DeleteCategoryTest(long role) throws Exception {
            categoryBody = new CategoryBody(category, login, journal);
        userRoles.getIdRole().setId(role);
        when(userRolesService.findByUserAndJournal(categoryBody.getLogin(), categoryBody.getJournalName())).thenReturn(userRoles);
        when(categoryService.findByNameAndIdJournal(categoryBody.getCategory(), categoryBody.getJournalName())).thenReturn(categoryClass);

        this.mvc.perform(post(path+"/deleteCategory")
                .content(String.valueOf(categoryBody))
                .contentType(MediaType.ALL))
                .andExpect(status().isCreated());
    }

    @Test
    public void DeleteCategoryTestAsKid() throws Exception {

        userRoles.getIdRole().setId(Constants.KidRole);
        given(userRolesService.findByUserAndJournal(login, journal)).willReturn(userRoles);
        given(categoryService.findByNameAndIdJournal(category, journal)).willReturn(categoryClass);

        mvc.perform(post("/deleteCategory")
                .contentType(MediaType.ALL))
                .andExpect(status().isCreated());
    }

    @Test
    public void DeleteCategoryTestWithError() throws Exception {

        userRoles.getIdRole().setId(Constants.KidRole);
        when(userRolesService.findByUserAndJournal(login, journal)).thenThrow(new IllegalStateException("Error occurred"));
        when(categoryService.findByNameAndIdJournal(category, journal)).thenThrow(new IllegalStateException("Error occurred"));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(categoryBody);

        mvc.perform(post("/private/addCategory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(categoryBody)))
                .andExpect(status().isInternalServerError());
    }
}