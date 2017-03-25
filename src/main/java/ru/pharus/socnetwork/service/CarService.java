package ru.pharus.socnetwork.service;

import ru.pharus.socnetwork.dao.CarsDao;
import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.ModelDao;
import ru.pharus.socnetwork.dao.UserDao;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Car;
import ru.pharus.socnetwork.entity.Model;
import ru.pharus.socnetwork.entity.Post;
import ru.pharus.socnetwork.entity.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

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

    public List<Model> getModels() throws DAOException {
        return modelDao.getAll();
    }

    public String validate(Car car) {
        StringBuilder err = new StringBuilder();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Car>> results = validator.validate(car);

        for (ConstraintViolation<Car> violation: results)
        {
            err.append(violation.getMessage()).append("<br>");
        }

        return err.toString();
    }

    public void addCar(Car car) throws DAOException {
        carsDao.create(car);
    }
}
