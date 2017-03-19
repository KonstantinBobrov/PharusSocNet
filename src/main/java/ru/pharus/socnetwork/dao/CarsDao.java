package ru.pharus.socnetwork.dao;

import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Car;

import java.util.List;

public interface CarsDao {
    int create(Car model) throws DAOException;
    void update(Car model) throws DAOException;
    void delete(int id) throws DAOException;
    List<Car> getAllById(int userId) throws DAOException;
}
