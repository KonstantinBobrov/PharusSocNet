package ru.pharus.socnetwork.dao;

import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserDao {

    int create(User user) throws DAOException;
    void update(User user) throws DAOException;
    List<User> getAll() throws DAOException;

    default Optional<User> getById(int id) throws DAOException{
        return getAll().stream().filter(T -> T.getId() == id)
                .findAny();
    }


}
