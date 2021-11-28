package fafij.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
