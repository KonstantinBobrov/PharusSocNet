package ru.pharus.socnetwork.dao;

import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Model;
import ru.pharus.socnetwork.entity.User;

import java.util.List;
import java.util.Optional;

public interface ModelDao {
    int create(Model model) throws DAOException;
    void update(Model model) throws DAOException;
    void delete(int id) throws DAOException;

    default Optional<Model> getById(int id) throws DAOException {
        return getAll().stream().filter(T -> T.getId() == id)
                .findAny();
    }
    List<Model> getAll() throws DAOException;
}
