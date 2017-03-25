package ru.pharus.socnetwork.controllers.listeners;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.exception.DAOException;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Web application deploying listener
 */
@WebListener
public class InitListener implements ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(InitListener.class);

    @Resource(name = "jdbc/TestDB")
    private DataSource dataSource;

    @SneakyThrows
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Start DaoFactory initialization");
        try {
            // Set TomCat JNDI DataSource for Dao layer
            DaoFactory.getInstanse().setDataSourceInjection(dataSource);
            DaoFactory.getInstanse().init(sce.getServletContext().getRealPath("/WEB-INF/classes"));
        } catch (DAOException e) {
            log.error("DaoFactory initialization failed", e);
        }
    }
}
