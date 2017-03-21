package ru.pharus.socnetwork.dao;

import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Car;

import java.util.List;
import java.util.stream.Collectors;

public interface CarsDao {
    int create(Car model) throws DAOException;
    void update(Car model) throws DAOException;
    void delete(int id) throws DAOException;
    List<Car> getByCriteria(String where) throws DAOException;
    default List<Car> getUserCars(int user) throws DAOException{
        return getByCriteria("").stream().filter(car -> car.getUserId() == user).collect(Collectors.toList());
    }
}
