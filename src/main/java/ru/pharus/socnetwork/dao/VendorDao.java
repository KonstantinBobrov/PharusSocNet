package ru.pharus.socnetwork.dao;

import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Vendor;

import java.util.List;
import java.util.Optional;


public interface VendorDao {

    int create(Vendor vendor) throws DAOException;

    void deleteById(int id) throws DAOException;

    default Optional<Vendor> getById(int id) throws DAOException{
        return getAll().stream().filter(vendor -> vendor.getId() == id)
                .findAny();
    }

    List<Vendor> getAll() throws DAOException;
}
