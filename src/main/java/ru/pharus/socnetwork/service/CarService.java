package ru.pharus.socnetwork.service;

import ru.pharus.socnetwork.dao.CarsDao;
import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.ModelDao;
import ru.pharus.socnetwork.dao.UserDao;

public class CarService {
    private DaoFactory factory = DaoFactory.getInstanse();
    private UserDao userDao = factory.getUserDao();
    private CarsDao carsDao = factory.getCarsDao();
    private ModelDao modelDao = factory.getModelDao();
}
