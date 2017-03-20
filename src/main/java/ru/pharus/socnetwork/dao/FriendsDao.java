package ru.pharus.socnetwork.dao;

import ru.pharus.socnetwork.dao.exception.DAOException;
import java.util.Set;

public interface FriendsDao {
    void addFriend(int user, int friend)  throws DAOException;
    void removeFriend(int user, int friend)  throws DAOException;
    Set<Integer> getUserFriendsId(int user)  throws DAOException;
}
