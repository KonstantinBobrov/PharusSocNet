package ru.pharus.socnetwork.dao.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.UserDao;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.User;
import ru.pharus.socnetwork.entity.enums.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDao implements UserDao {
    private static final Logger log = LoggerFactory.getLogger(SQLUserDao.class);
    private DaoFactory factory = DaoFactory.getInstanse();

    @Override
    public int create(User user) throws DAOException {
        if (user != null) {
            log.debug(String.format("Creating new user %s", user.getLogin()));

            String sql = "INSERT INTO drivers(login, password, full_name, city, birth_date, register_date, role) VALUES (?,?,?,?,?,?,?)";
            try (Connection conn = factory.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                statement.setObject(1, user.getLogin());
                statement.setObject(2, user.getPassword());
                statement.setObject(3, user.getFullName());
                statement.setObject(4, user.getCity());
                statement.setObject(5, user.getBirthDate());
                statement.setObject(6, user.getRegisterDate());
                statement.setObject(7, user.getRole());

                log.trace("Open connection and statement. Execute insert query");
                statement.executeUpdate();
                user.setId(statement.getGeneratedKeys().getInt(1));

                return user.getId();

            } catch (SQLException e) {
                log.warn(String.format("SQL error: cannot to create new user %s", user.getLogin()), e);
                throw new DAOException("SQL error: cannot to create new user", e);
            }
        }

        return -1;
    }

    @Override
    public void update(User user) throws DAOException {
        if (user != null && user.getId() > 0) {
            log.debug(String.format("Updating user %s", user.getLogin()));

            String sql =
                    "UPDATE drivers set login = ?, password = ?, full_name = ?, city = ?, birth_date = ?, register_date = ?, role = ?) " +
                            "WHERE id = " + user.getId();

            try (Connection conn = factory.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setObject(1, user.getLogin());
                statement.setObject(2, user.getPassword());
                statement.setObject(3, user.getFullName());
                statement.setObject(4, user.getCity());
                statement.setObject(5, user.getBirthDate());
                statement.setObject(6, user.getRegisterDate());
                statement.setObject(7, user.getRole());
                statement.executeUpdate();
            } catch (SQLException e) {
                log.warn(String.format("SQL error: cannot to update user %s", user.getLogin()), e);
                throw new DAOException("SQL error: cannot to update user", e);
            }
        }

    }

    @Override
    public List<User> getAll() throws DAOException {
        List<User> usersList = new ArrayList<>();
        String sql = "SELECT id, login, password, full_name, city, birth_date, register_date, role FROM drivers";

        try (Connection conn = factory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()){
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("full_name"),
                        resultSet.getString("city"),
                        resultSet.getDate("birth_date").toLocalDate(),
                        resultSet.getDate("register_date").toLocalDate(),
                        Role.valueOf(resultSet.getString("role"))
                );
                usersList.add(user);
            }

        } catch (SQLException e) {

        }

        return usersList;
    }

    @Override
    public void addFriend(int id) throws DAOException {

    }

    @Override
    public void removeFriend(int id) throws DAOException {

    }

    @Override
    public List<Integer> getFriends() throws DAOException {
        return null;
    }
}
