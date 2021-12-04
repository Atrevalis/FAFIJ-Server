package fafij.server.controllers;

import fafij.server.controllers.AuthenticationController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTests{

    @Autowired
    private MockMvc mvc;

   /* @Test
    public void getVehicleShouldReturnMakeAndModel() {
        given(this.controller.createAuthToken())
                .willReturn(new VehicleDetails("Honda", "Civic"));

        this.mvc.perform(get("/sboot/vehicle")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("Honda Civic"));
    }*/
}
