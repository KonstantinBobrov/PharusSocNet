package ru.pharus.socnetwork.dao;

import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Car;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data object interface for Car entity
 *
 * All methods may @throws DAOException by default
 */
public interface CarsDao {

    /**
     * Create new entity in database
     * @param model - new {@link Car} entity
     * @return - return generated id key

     */
    int create(Car model) throws DAOException;

    /**
     * Update saved database entity
     * @param model - {@link Car} entity
     */
    void update(Car model) throws DAOException;

    /**
     * Delete {@link Car} entity from database
     * @param id - integer identifier of deleted object
     */
    void delete(int id) throws DAOException;

    /**
     *
     * @param where
     * @return
     */
    List<Car> getByCriteria(String where) throws DAOException;

    /**
     *
     * @param user
     * @return - returned collection
     */
    default List<Car> getUserCars(int user) throws DAOException{
        return getByCriteria("").stream().filter(car -> car.getUserId() == user).collect(Collectors.toList());
    }
}
