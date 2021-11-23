package fafij.server;

import fafij.server.entity.Users;
import fafij.server.Repository.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ServerApplication {
    /*@Autowired
    private UserService userService;*/

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

  /*  @EventListener(ApplicationReadyEvent.class)
    private void testJpaMethods(){
        Users user = new Users();
        user.setLogin("qwerty");
        user.setPassword("ytrewq");
        userService.createUsers(user);

        userService.findAllByLogin("qwerty").forEach(it->System.out.println(it));
    }*/
}
