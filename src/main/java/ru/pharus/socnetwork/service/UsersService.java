package ru.pharus.socnetwork.service;

import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.PostDao;
import ru.pharus.socnetwork.dao.UserDao;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Post;
import ru.pharus.socnetwork.entity.User;

import java.util.List;

public class UsersService {
    private DaoFactory factory = DaoFactory.getInstanse();
    private UserDao userDao = factory.getUserDao();
    private PostDao postDao = factory.getPostDao();

    public User getUserByLogin(String login) throws DAOException{
        return userDao.getByCriteria(String.format(" where login='%s'", login)).stream().findFirst().orElse(null);
    }

    public List<Post> getUserPosts(User user) throws DAOException{
        return postDao.getUserPost(user.getId());
    }
}
