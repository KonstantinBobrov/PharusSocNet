package ru.pharus.socnetwork.dao;

import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.model.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverDao {

    int create(Driver driver) throws DAOException;
    void update(Driver driver) throws DAOException;
    List<Driver> getAll() throws DAOException;
    void addFriend(int id) throws DAOException;
    void removeFriend(int id) throws DAOException;
    List<Integer> getAllFriend() throws DAOException;

    default Optional<Driver> getById(int id) throws DAOException{
        return getAll().stream().filter(T -> T.getId() == id)
                .findAny();
    }


}
