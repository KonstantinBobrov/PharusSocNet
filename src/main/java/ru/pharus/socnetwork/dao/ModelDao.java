package ru.pharus.socnetwork.dao;

import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Model;
import java.util.List;
import java.util.Optional;

public interface ModelDao {
    int create(Model model) throws DAOException;
    void update(Model model) throws DAOException;
    void delete(int id) throws DAOException;
    List<Model> getByCriteria(String where) throws DAOException;
    List<Model> getAll() throws DAOException;
    default Optional<Model> getById(int id) throws DAOException {
        return getAll().stream().filter(T -> T.getId() == id)
                .findAny();
    }
}
