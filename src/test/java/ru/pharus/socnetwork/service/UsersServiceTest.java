package ru.pharus.socnetwork.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Message;
import ru.pharus.socnetwork.entity.Post;
import ru.pharus.socnetwork.entity.User;
import ru.pharus.socnetwork.entity.enums.Role;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceTest {
    private DaoFactory factory;
    private UsersService usersService;
    private User testUser;

    @BeforeEach
    void setUp() throws DAOException {
        factory = DaoFactory.getInstance();
        factory.init("src/test/resources");
        usersService = new UsersService();
        testUser = usersService.getUserById(1);
    }

    @Test
    void getUserByLogin() throws DAOException {

        User user = usersService.getUserByLogin("pharus@mail.ru");
        assertNotNull(user);

        User user2 = usersService.getUserByLogin("incorrect@google.com");
        assertNull(user2);
    }

    @Test
    void getUserPosts() throws DAOException {
        List<Post> list = usersService.getUserPosts(usersService.getUserById(3));
        assertNotNull(list);
        assertTrue(list.size() == 1);
    }

    @Test
    void getUserById() throws DAOException {
        User user = usersService.getUserById(1);
        assertNotNull(user);
        assertEquals(user.getLogin(), "pharus@mail.ru");
    }

    @Test
    void getUserFriends() throws DAOException{
        List<User> list = usersService.getUserFriends(testUser);
        assertTrue(list.size() == 7);
    }

    @Test
    void addPost() throws DAOException {
        Post post = new Post();
        post.setUserId(1);
        post.setText("Test post");
        usersService.addPost(post);
        Post result = usersService.getPostById(post.getId());
        assertEquals(result.getText(), "Test post");
    }

    @Test
    void getPostById() throws DAOException {
        Post post = usersService.getPostById(2);
        assertNotNull(post);
        Post post2 = usersService.getPostById(13);
        assertNull(post2);
    }

    @Test
    void editPost() throws DAOException {
        Post post = usersService.getPostById(2);
        post.setText("edit text");
        usersService.editPost(post);
        Post result = usersService.getPostById(2);
        assertEquals(result.getText(), "edit text");
    }

    @Test
    void deletePost() throws DAOException{
        usersService.deletePost(1);
        assertNull(usersService.getPostById(1));
    }

    @Test
    void isSubcribed() throws DAOException {
        User user = usersService.getUserById(3);
        assertTrue(usersService.isSubcribed(testUser, user));
        assertFalse(usersService.isSubcribed(user, testUser));

    }

    @Test
    void subscribe() throws DAOException {
        User user = usersService.getUserById(4);
        assertFalse(usersService.isSubcribed(user, testUser));
        usersService.subscribe(user, testUser);
        assertTrue(usersService.isSubcribed(user, testUser));
    }

    @Test
    void unsubscribe() throws DAOException {
        User user = usersService.getUserById(2);
        assertTrue(usersService.isSubcribed(user, testUser));
        usersService.unsubscribe(user, testUser);
        assertFalse(usersService.isSubcribed(user, testUser));
    }

    @Test
    void register() throws DAOException {
        User user = new User();
        user.setLogin("test@test.ru");
        user.setPassword("123");
        user.setFullName("Ivanov");
        user.setRole(Role.USER);
        usersService.register(user);
        assertNotNull(user.getId());
    }

    @Test
    void updateUser() throws DAOException {
        User user = usersService.getUserById(2);
        user.setFullName("Petrov");
        usersService.updateUser(user);
        User result = usersService.getUserById(2);
        assertEquals(result.getFullName(), "Petrov");
    }

    @Test //Validate message
    void validate() {
    }

    @Test
    void validate1() {
    }

    @Test
    void validatePost() {
    }

    @Test
    void getUserNews() {
    }

    @Test
    void sendMessage() throws DAOException {
        Message message = new Message();
        message.setFromUserId(1);
        message.setToUserId(2);
        message.setMessage("Test message");
        message.setPostTime(LocalDateTime.now());
        usersService.sendMessage(message);

    }

    @Test
    void getUserMessages() {
    }

    @Test
    void getUserMessages1() {
    }

    @Test
    void getAll() throws DAOException {
        List<User> list = usersService.getAll();
        assertTrue(list.size() == 9);
    }

}