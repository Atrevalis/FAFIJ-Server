package fafij.server;

import fafij.server.controllers.*;
import fafij.server.repository.*;
import fafij.server.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
class ServerApplicationTests {

    @Test
    void contextLoadsRepositories() {
        assertNotNull(categoryRepository);
        assertNotNull(invitationsRepository);
        assertNotNull(journalRepository);
        assertNotNull(noteRepository);
        assertNotNull(rolesRepository);
        assertNotNull(userRolesRepository);
        assertNotNull(usersRepository);
    }
    @Test
    void contextLoadsServices() {
        assertNotNull(categoryService);
        assertNotNull(invitationsService);
        assertNotNull(journalService);
        assertNotNull(noteService);
        assertNotNull(rolesService);
        assertNotNull(userRolesService);
        assertNotNull(userService);
    }

    @Test
    void contextLoadsControllers() {
        assertNotNull(authenticationController);
        assertNotNull(categoryController);
        assertNotNull(journalController);
        assertNotNull(noteController);
        assertNotNull(userController);
    }
    @Autowired
    private AuthenticationController authenticationController;
    @Autowired
    private CategoryController categoryController;
    @Autowired
    private JournalController journalController;
    @Autowired
    private NoteController noteController;
    @Autowired
    private UserController userController;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private InvitationsRepository invitationsRepository;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UserRolesRepository userRolesRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private InvitationsService invitationsService;
    @Autowired
    private JournalService journalService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private RolesService rolesService;
    @Autowired
    private UserRolesService userRolesService;
    @Autowired
    private UserService userService;
}
