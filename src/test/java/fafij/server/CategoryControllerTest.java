package fafij.server;


import fafij.server.controllers.AuthenticationController;
import fafij.server.controllers.CategoryController;
import fafij.server.entity.Roles;
import fafij.server.entity.UserRoles;
import fafij.server.utils.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CategoryController categoryController;


/*
    @Test
    public void test()throws Exception{
        Roles roles = new Roles();
        UserRoles userRoles = new UserRoles();
        userRoles.setIdRole(roles);
        userRoles.getIdRole().setId(Constants.KidRole);
        given(userRolesService.findByUserAndJournal(login, journalName)).willReturn(userRoles);

        this.mvc.perform(mvc.perform(post("/addCategory"))
                .andDo(print(status().isNotAcceptable()))
                .andExpect();
    }*/

}
