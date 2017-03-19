package ru.pharus.socnetwork.dao.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.VendorDao;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Vendor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SQLVendorDao implements VendorDao {
    private static final Logger log = LoggerFactory.getLogger(SQLVendorDao.class);
    private DaoFactory factory = DaoFactory.getInstanse();

    @Override
    public int create(Vendor vendor) throws DAOException {
        log.debug(String.format("Creating new vendor %s", vendor.getName()));
        String sql = "insert into vendors(name) values(?)";

        if (vendor != null) {
            try (Connection conn = factory.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setObject(1, vendor.getName());
                log.trace("Open connection and statement. Execute query: insert vendor");
                statement.execute();
                vendor.setId(statement.getGeneratedKeys().getInt(1));
                return vendor.getId();

            } catch (SQLException e) {
                throw new DAOException("", e);
            }
        }
        return 0;
    }

    @Override
    public void update(Vendor vendor) throws DAOException {
        if (vendor != null && vendor.getId() > 0) {
            log.debug(String.format("Updating user vendor %s", vendor.getName()));

            String sql =
                    "UPDATE vendors set name = ? WHERE id = " + vendor.getId();

            try (Connection conn = factory.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setObject(1, vendor.getName());
                log.trace("Open connection and statement. Execute query: update vendor");
                statement.executeUpdate();

            } catch (SQLException e) {
                log.warn(String.format("SQL error: cannot to update vendor %s", vendor.getName()), e);
                throw new DAOException("SQL error: cannot to update user", e);
            }
        }
    }

    @Override
    public void deleteById(int id) throws DAOException {
        log.debug(String.format("Deleting vendor with id %s", id));
        String sql = "delete from vendors where id = (?)";

        try (Connection conn = factory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setObject(1, id);
            log.trace("Open connection and statement. Execute query: delete vendor");
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn("SQL error: cannot delete vendor", e);
            throw new DAOException("SQL error: cannot delete vendor", e);
        }
    }

    @Override
    public List<Vendor> getAll() throws DAOException {
        List<Vendor> list = new ArrayList<>();
        String sql = "SELECT id, name FROM vendors";

        try (Connection conn = factory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            log.trace("Open connection and statement. Execute query: select vendors");

            while (resultSet.next()){
                Vendor vendor = new Vendor(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                );
                list.add(vendor);
            }

        } catch (SQLException e) {
            log.warn("SQL error: cannot select users", e);
            throw new DAOException("SQL error: cannot select users", e);
        }

        return list;
    }
}
