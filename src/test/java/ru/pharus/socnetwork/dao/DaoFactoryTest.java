package ru.pharus.socnetwork.dao;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class DaoFactoryTest {
    @Test
    void getInstance() {
        assertNotNull(DaoFactory.getInstance());
    }

    @Test
    @SneakyThrows
    void setDataSourceInjection() {
        //DaoFactory.getInstance().setDataSourceInjection(null);
        //assertNull(DaoFactory.getInstance().getConnection());
    }


    @Test
    @SneakyThrows
    void getConnection() {
        assertNotNull(DaoFactory.getInstance().getConnection());
    }



    @Test
    void init() {
    }

    @Test
    void getSingleConnection() {
    }

    @Test
    void getUserDao() {
        assertNotNull(DaoFactory.getInstance().getUserDao());
    }

    @Test
    void getFriendsDao() {
        assertNotNull(DaoFactory.getInstance().getFriendsDao());
    }

    @Test
    void getMessageDao() {
        assertNotNull(DaoFactory.getInstance().getMessageDao());
    }

    @Test
    void getPostDao() {
        assertNotNull(DaoFactory.getInstance().getPostDao());
    }

    @Test
    void getCarsDao() {
        assertNotNull(DaoFactory.getInstance().getCarsDao());
    }

    @Test
    void getModelDao() {
        assertNotNull(DaoFactory.getInstance().getModelDao());
    }

}