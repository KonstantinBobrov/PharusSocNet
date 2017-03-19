package ru.pharus.socnetwork.dao.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.VendorDao;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Vendor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class SQLVendorDao implements VendorDao {
    static final Logger log = LoggerFactory.getLogger(SQLVendorDao.class);

    private DaoFactory factory = DaoFactory.getInstanse();

    @Override
    public int create(Vendor vendor) throws DAOException {
        String sql = "insert into vendor(name) values(?)";

        try(Connection conn = factory.getConnection()) {

        } catch (SQLException e) {
            throw new DAOException("" , e);
        }

        return 0;
    }

    @Override
    public void deleteById(int id) throws DAOException {

    }

    @Override
    public List<Vendor> getAll() throws DAOException {
        return null;
    }
}
