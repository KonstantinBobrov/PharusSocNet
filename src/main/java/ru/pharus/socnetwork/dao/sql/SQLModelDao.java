package ru.pharus.socnetwork.dao.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.ModelDao;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLModelDao implements ModelDao {
    private static final Logger log = LoggerFactory.getLogger(SQLModelDao.class);
    private DaoFactory factory = DaoFactory.getInstanse();

    @Override
    public int create(Model model) throws DAOException {
        if (model == null) {
            log.warn("Method create got NullPointer car Model entity");
            return 0;
        }

        log.debug(String.format("Creating new car model %s" ,model.getName()));

        String sql = "INSERT INTO models(name) VALUES (?)";
        try (Connection conn = factory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setObject(1, model.getName());

            log.trace("Open connection and statement. Execute query: insert model");
            statement.executeUpdate();
            model.setId(statement.getGeneratedKeys().getInt(1));

            return model.getId();

        } catch (SQLException e) {
            log.warn(String.format("SQL error: cannot add model %s", model.getName()), e);
            throw new DAOException("SQL error: cannot add model ", e);
        }
    }

    @Override
    public void update(Model model) throws DAOException {
        if (model == null || model.getId() == 0) {
            log.warn("Method update got NullPointer user Car entity");
            return;
        }

        log.debug(String.format("Updating car model with id %s", model.getId()));

        String sql = "UPDATE models set name = ? WHERE id = " + model.getId();

        try (Connection conn = factory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setObject(1, model.getName());
            log.trace("Open connection and statement. Execute query: update car model");
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn("SQL error: cannot to update car model", e);
            throw new DAOException("SQL error: cannot to update car model", e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        if (id < 1) return;

        log.debug(String.format("deleting car model %d", id));

        String sql = "Delete from models where id=" + id;

        try(Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn(String.format("SQL error: cannot to delete car model %s", id), e);
            throw new DAOException("SQL error: cannot to delete car model", e);
        }
    }

    @Override
    public List<Model> getAll() throws DAOException {
        log.debug("Get all car models");
        return getByCriteria(" ");
    }

    @Override
    public List<Model> getByCriteria(String where) throws DAOException {
        log.debug(String.format("Get car models by criteria %s", where));
        List<Model> list = new ArrayList<>();

        String sql = "SELECT id, name FROM models " + where;

        try (Connection conn = factory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            log.trace("Open connection and statement. Execute query: select models");

            while (resultSet.next()) {
                Model model = new Model(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                );
                list.add(model);
            }

            log.debug(String.format("Done. Close connection. Return %d car models", list.size()));
            return list;
        } catch (SQLException e) {
            log.warn("SQL error: cannot select car models", e);
            throw new DAOException("SQL error: cannot select car models", e);
        }


    }
}
