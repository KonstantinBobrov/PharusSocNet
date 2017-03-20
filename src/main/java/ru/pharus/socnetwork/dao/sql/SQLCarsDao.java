package ru.pharus.socnetwork.dao.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.CarsDao;
import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLCarsDao implements CarsDao {
    private static final Logger log = LoggerFactory.getLogger(SQLCarsDao.class);
    private DaoFactory factory = DaoFactory.getInstanse();

    @Override
    public int create(Car model) throws DAOException {
        if (model == null) {
            log.warn("Method create got NullPointer Car entity");
            return 0;
        }

        log.debug(String.format("Creating new car for user %s" ,model.getUserId()));

        String sql = "INSERT INTO cars(user_id, model_id, car_year, car_number) VALUES (?,?,?,?)";
        try (Connection conn = factory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setObject(1, model.getUserId());
            statement.setObject(2, model.getModelId());
            statement.setObject(3, model.getYear());
            statement.setObject(4, model.getCarNumber());

            log.trace("Open connection and statement. Execute query: insert car");
            statement.executeUpdate();
            model.setId(statement.getGeneratedKeys().getInt(1));

            return model.getId();

        } catch (SQLException e) {
            log.warn(String.format("SQL error: cannot add car for user %s", model.getUserId()), e);
            throw new DAOException("SQL error: cannot add car for user", e);
        }
    }

    @Override
    public void update(Car model) throws DAOException {
        if (model == null && model.getId() == 0) {
            log.warn("Method update got NullPointer user Car entity");
            return;
        }

        log.debug(String.format("Updating user car with id %s", model.getId()));

        String sql =
                "UPDATE cars set user_id = ?, model_id = ?, car_year = ?, car_number = ?) " +
                        "WHERE id = " + model.getId();

        try (Connection conn = factory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setObject(1, model.getUserId());
            statement.setObject(2, model.getModelId());
            statement.setObject(3, model.getYear());
            statement.setObject(4, model.getCarNumber());
            log.trace("Open connection and statement. Execute query: update user car");
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn(String.format("SQL error: cannot to update user car %s", model.getId()), e);
            throw new DAOException("SQL error: cannot to update user car", e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        if (id < 1) return;

        log.debug(String.format("deleting user car with id %d", id));

        String sql = "Delete from cars where id=" + id;

        try(Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn(String.format("SQL error: cannot to delete user car %s", id), e);
            throw new DAOException("SQL error: cannot to delete user car", e);
        }
    }

    @Override
    public List<Car> geUserCars(int user) throws DAOException {
        log.debug("Get user cars");
        return getByCriteria("user_id = " + user);
    }

    @Override
    public List<Car> getByCriteria(String where) throws DAOException {
        log.debug(String.format("Get cars by criteria %s", where));
        List<Car> list = new ArrayList<>();

        String sql = "SELECT id, user_id, model_id, car_year, car_number  FROM cars " + where;

        try (Connection conn = factory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            log.trace("Open connection and statement. Execute query: select users");

            while (resultSet.next()) {
                Car model = new Car(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("model_id"),
                        resultSet.getInt("car_year"),
                        resultSet.getString("car_number")
                );
                list.add(model);
            }

        } catch (SQLException e) {
            log.warn("SQL error: cannot select users", e);
            throw new DAOException("SQL error: cannot select users", e);
        }

        return list;
    }
}
