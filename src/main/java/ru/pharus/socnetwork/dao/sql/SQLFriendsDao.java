package ru.pharus.socnetwork.dao.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.FriendsDao;
import ru.pharus.socnetwork.dao.exception.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SQLFriendsDao implements FriendsDao {
    private static final Logger log = LoggerFactory.getLogger(SQLFriendsDao.class);
    private DaoFactory factory = DaoFactory.getInstance();

    @Override
    public void addFriend(int user, int friend) throws DAOException {
        log.debug(String.format("Add friend for user %d", user));
        if (user < 1 || friend < 1) return;

        String sql = "Insert into friends(id_user, id_friend) values(?,?)";

        try(Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setObject(1, user);
            statement.setObject(2, friend);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQL error: insert into friends table");
            throw  new DAOException("SQL error: insert into friends table", e);
        }

    }

    @Override
    public void removeFriend(int user, int friend) throws DAOException {
        log.debug(String.format("Remove friend for user %d", user));

        String sql = "DELETE from friends WHERE id_user = ? AND id_friend = ?";

        try(Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setObject(1,user);
            statement.setObject(2,friend);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQL error: delete from friends table");
            throw  new DAOException("SQL error: delete from friends table", e);
        }
    }
    @Override
    public Set<Integer> getUserFriendsId(int user) throws DAOException {
        log.debug(String.format("Get all friends for user %d", user));

        String sql = "SELECT id, id_friend FROM friends WHERE id_user="+user;

        try(Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){

            Set<Integer> set = new HashSet<>();

            while (resultSet.next()){
                set.add(resultSet.getInt("id_friend"));
            }

            return set;
        } catch (SQLException e) {
            log.error("SQL error: select from friends table");
            throw  new DAOException("SQL error: select from friends table", e);
        }
    }
}
