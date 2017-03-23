package ru.pharus.socnetwork.service;

import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.FriendsDao;
import ru.pharus.socnetwork.dao.PostDao;
import ru.pharus.socnetwork.dao.UserDao;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Post;
import ru.pharus.socnetwork.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UsersService {
    private DaoFactory factory = DaoFactory.getInstanse();
    private UserDao userDao = factory.getUserDao();
    private PostDao postDao = factory.getPostDao();
    private FriendsDao friendsDao = factory.getFriendsDao();

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
        List<User> userList = friendsDao.getUserFriendsId(currUser.getId()).parallelStream().map(id -> {
            User user = new User();
            try {
                user = userDao.getById(id);
            }catch (DAOException e){

            } return user;}).collect(Collectors.toList());

        return userList;
    }
}
