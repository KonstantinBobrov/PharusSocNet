package ru.pharus.socnetwork.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.exception.DAOException;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Singleton DaoFactory class for Dao layer
 *
 */
public class DaoFactory {
    private static final Logger log = LoggerFactory.getLogger(DaoFactory.class);
    private static volatile DaoFactory daoINSTANCE;

    // TomCat JNDI DataSource does not work here
    // @Resource(name = "jdbc/TestDB")
    private DataSource dataSource;

    private DaoFactory(){
    }

    public static DaoFactory getInstanse(){
        DaoFactory instance = daoINSTANCE;
        if (instance == null){
            synchronized (DaoFactory.class){
                instance = daoINSTANCE;
                if(instance == null){
                    log.info("Creating DaoFactory instance");
                    daoINSTANCE = instance = new DaoFactory();
                }
            }
        }
        return instance;
    }

    public void setDataSourceInjection (DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void init(String resourcesDbProperties) throws DAOException{
        initH2Db(resourcesDbProperties);
    }

    public Connection getConnection() throws DAOException{
        log.debug("DaoFactory get connection");
        // TODO: 18.03.2017 AddConnectionPool
        try {
            return dataSource.getConnection();
        }catch (SQLException e){
            //log.error("Connection error. No connection to DB", e);
            throw new DAOException("Connection error. No connection to DB", e);
        }

    }

    private void initH2Db(String resourcesDbProperties){
        log.debug("H2DB initialisation from SQL files");

        // TODO: 19.03.2017 Reading from file move to core module

        try{
            Path pathSource = Paths.get(resourcesDbProperties + "/h2_sql");

            if (Files.isDirectory(pathSource)){
                List<Path> pathList = Files.list(pathSource)
                        .filter(file -> file.toString().endsWith(".sql"))
                        .collect(Collectors.toList());

                try(Connection conn = getConnection();
                    Statement statement = conn.createStatement()) {
                    for (Path path : pathList) {
                    log.debug(String.format("Execute H2DB file initialization: %s", path.toString()));
                        String collect = Files.readAllLines(path).stream()
                                .collect(Collectors.joining());
                        statement.executeUpdate(collect);
                    }
                }catch (SQLException | DAOException e){
                    log.error("SQL error initialization H2DB database", e);
                }

            }else{
                log.info(String.format("Path for sql resources %s not exists",pathSource));
            }

        }catch (IOException e){
            log.error("IOException in", e);
        }
    }
}
