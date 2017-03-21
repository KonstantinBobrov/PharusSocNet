package ru.pharus.socnetwork.service;


import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.UserDao;

public class FriendsService {
    private DaoFactory factory = DaoFactory.getInstanse();
    private UserDao userDao = factory.getUserDao();

}
