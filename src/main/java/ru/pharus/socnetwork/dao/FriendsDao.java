package ru.pharus.socnetwork.dao;

import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.User;
import java.util.Collection;

public interface FriendsDao {
    void addFriend(int user, int friend)  throws DAOException;
    void removeFriend(int user, int friend)  throws DAOException;
    Collection<User> getFriendsById(int user)  throws DAOException;
}
