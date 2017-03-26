package ru.pharus.socnetwork.service;

import ru.pharus.socnetwork.dao.*;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Message;
import ru.pharus.socnetwork.entity.Post;
import ru.pharus.socnetwork.entity.User;

import javax.validation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UsersService {
    private static Validator validator;
    private DaoFactory factory = DaoFactory.getInstance();
    private UserDao userDao = factory.getUserDao();
    private PostDao postDao = factory.getPostDao();
    private FriendsDao friendsDao = factory.getFriendsDao();
    private MessageDao messageDao = factory.getMessageDao();

    public User getUserByLogin(String login) throws DAOException{
        return userDao.getByCriteria(String.format(" where login='%s'", login)).stream().findFirst().orElse(null);
    }

    public List<Post> getUserPosts(User user) throws DAOException{
        return postDao.getUserPost(user.getId());
    }

    public User getUserById(int id) throws DAOException {
        return userDao.getById(id);
    }

    public List<User> getUserFriends(User currUser) throws DAOException {
        // будет замечательно если заработает :)
        return friendsDao.getUserFriendsId(currUser.getId()).parallelStream().map(id -> {
            User u = new User();
            try {
                u = userDao.getById(id);
            }catch (DAOException e){
            } return u;})
                .collect(Collectors.toList());
    }

    public void deletePost(int id) throws DAOException{
        postDao.delete(id);
    }

    public void addPost(Post post) throws DAOException{
        post.setTitle("");
        postDao.create(post);
    }

    public Post getPostById(int id) throws DAOException {
        return postDao.getById(id);
    }

    public void editPost(Post post) throws DAOException {
        post.setTitle("");
        postDao.update(post);
    }

    public boolean isSubcribed(User user, User infoUser) throws DAOException {
        return  friendsDao.getUserFriendsId(user.getId()).contains(infoUser.getId());
    }

    public void subscribe(User user1, User user2) throws DAOException {
        friendsDao.addFriend(user1.getId(), user2.getId());
    }
    public void unsubscribe(User user1, User user2) throws DAOException {
        friendsDao.removeFriend(user1.getId(), user2.getId());
    }


    public void register(User user) throws DAOException {
        userDao.create(user);
    }

    public void updateUser(User user) throws DAOException {
        userDao.update(user);
    }

    public String validate(User user) {
        StringBuilder err = new StringBuilder();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> results = validator.validate(user);

        for (ConstraintViolation<User> violation: results)
        {
        err.append(violation.getMessage()).append("<br>");
        }
        
        return err.toString();
    }

    public String validate(Message message) {
        StringBuilder err = new StringBuilder();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Message>> results = validator.validate(message);
        for (ConstraintViolation<Message> violation: results)
        {
            err.append(violation.getMessage()).append("<br>");
        }
        return err.toString();
    }

    public String validatePost(Post post) {
        StringBuilder err = new StringBuilder();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Post>> results = validator.validate(post);

        for (ConstraintViolation<Post> violation: results)
        {
            err.append(violation.getMessage()).append("<br>");
        }

        return err.toString();
    }

    public List<Post> getUserNews(User logUser) throws DAOException {
        List<Post> list;
        list = postDao.getByCriteria(String.format(" INNER JOIN friends fr ON fr.id_friend = user_id WHERE fr.id_user=%d", logUser.getId()));
        return list;
    }

    public void sendMessage(Message message) throws DAOException{
        messageDao.create(message);
    }

    public List<Message> getUserMessages(User user) throws DAOException{
        //" WHERE from_user_id=%d OR to_user_id=%d GROUP BY to_user_id,from_user_id" почему то не работает на h2 базе! на mysql ном
        return messageDao.getMessagesByCriteria(String.format(" WHERE from_user_id=%d OR to_user_id=%d", user.getId(),user.getId()));
        //return messageDao.getMessagesByUser(user.getId());
    }

    public List<Message> getUserMessages(User fromUser, User toUser) throws DAOException{
        String sql = String.format(" WHERE from_user_id=%d AND to_user_id=%d OR from_user_id=%d AND to_user_id=%d GROUP BY id",
                fromUser.getId(),toUser.getId(), toUser.getId(),fromUser.getId() );
        return messageDao.getMessagesByCriteria(sql);
    }

    public List<User> getAll() throws DAOException{
        return userDao.getAll();
    }
}
