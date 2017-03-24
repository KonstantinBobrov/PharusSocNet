package ru.pharus.socnetwork.service;

import ru.pharus.socnetwork.dao.CarsDao;
import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.ModelDao;
import ru.pharus.socnetwork.dao.UserDao;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Car;
import ru.pharus.socnetwork.entity.Model;
import ru.pharus.socnetwork.entity.User;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CarService {
    private DaoFactory factory = DaoFactory.getInstanse();
    private UserDao userDao = factory.getUserDao();
    private CarsDao carsDao = factory.getCarsDao();
    private ModelDao modelDao = factory.getModelDao();


    public List<Car> getUserCars(User user) throws DAOException {
        return carsDao.getUserCars(user.getId());
    }

    public Model getModelById(int id) throws DAOException {
        return modelDao.getById(id).orElse(null);
    }

    public void deleteCar(int id) throws DAOException {
        carsDao.delete(id);
    }
}
