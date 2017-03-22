package ru.pharus.socnetwork.dao.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.UserDao;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.User;
import ru.pharus.socnetwork.entity.enums.Role;

public class SQLUserDao implements UserDao {
    private static final Logger log = LoggerFactory.getLogger(SQLUserDao.class);
    private DaoFactory factory = DaoFactory.getInstanse();

    @Override
    public int create(User user) throws DAOException {
        if (user == null) {
            log.warn("Method create got NullPointer User entity");
            return 0;
        }

        log.debug(String.format("Creating new user %s", user.getLogin()));

        String sql = "INSERT INTO users(login, password, full_name, birth_date, register_date, role) VALUES (?,?,?,?,?,?)";
        try (Connection conn = factory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setObject(1, user.getLogin());
            statement.setObject(2, user.getPassword());
            statement.setObject(3, user.getFullName());
            statement.setObject(4, user.getBirthDate());
            statement.setObject(5, user.getRegisterDate());
            statement.setObject(6, user.getRole());

            log.trace("Open connection and statement. Execute query: insert user");
            statement.executeUpdate();
            user.setId(statement.getGeneratedKeys().getInt(1));

            return user.getId();

        } catch (SQLException e) {
            log.warn(String.format("SQL error: cannot to create new user %s", user.getLogin()), e);
            throw new DAOException("SQL error: cannot to create new user", e);
        }
    }

    @Override
    public void update(User user) throws DAOException {

        if (user == null || user.getId() == 0) {
            log.warn("Method update got NullPointer User entity");
            return;
        }

        log.debug(String.format("Updating user %s", user.getLogin()));

        String sql =
                "UPDATE users set login = ?, password = ?, full_name = ?, birth_date = ?, register_date = ?, role = ?) " +
                        "WHERE id = " + user.getId();

        try (Connection conn = factory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setObject(1, user.getLogin());
            statement.setObject(2, user.getPassword());
            statement.setObject(3, user.getFullName());
            statement.setObject(4, user.getBirthDate());
            statement.setObject(5, user.getRegisterDate());
            statement.setObject(6, user.getRole());
            log.trace("Open connection and statement. Execute query: update user");
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn(String.format("SQL error: cannot to update user %s", user.getLogin()), e);
            throw new DAOException("SQL error: cannot to update user", e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        if (id < 1) return;

        log.debug(String.format("deleting user with id %d", id));

        String sql = "Delete from users where id=" + id;

        try(Connection conn = factory.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn(String.format("SQL error: cannot to delete user %s", id), e);
            throw new DAOException("SQL error: cannot to delete user", e);
        }
    }

    @Override
    public User getById(int id) throws DAOException {
        log.debug(String.format("Get user by user.id %d", id));
        return getByCriteria(" where id = " + id).stream().findFirst().orElse(null);
    }

    public List<User> getByCriteria(String where) throws DAOException {
        log.debug(String.format("Get user by criteria %s", where));

        List<User> usersList = new ArrayList<>();
        String sql = "SELECT id, login, password, full_name, birth_date, register_date, role FROM users " + where;

        try (Connection conn = factory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            log.trace("Open connection and statement. Execute query: select users");

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("full_name"),
                        resultSet.getDate("birth_date").toLocalDate(),
                        resultSet.getTimestamp("register_date").toLocalDateTime(),
                        Role.valueOf(resultSet.getString("role"))
                );
                usersList.add(user);
            }

            log.debug(String.format("Done. Close connection. Return %d users", usersList.size()));
            return usersList;
        } catch (SQLException e) {
            log.warn("SQL error: cannot select users", e);
            throw new DAOException("SQL error: cannot select users", e);
        }
    }
}
