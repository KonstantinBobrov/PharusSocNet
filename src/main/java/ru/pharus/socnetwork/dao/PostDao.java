package ru.pharus.socnetwork.dao;

import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Post;

import java.util.List;

public interface PostDao {
    int create(Post message) throws DAOException;

    void update(Post message) throws DAOException;

    void delete(int id) throws DAOException;

    List<Post> getUserPost(int user) throws DAOException;
}
