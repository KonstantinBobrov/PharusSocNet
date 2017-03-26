package ru.pharus.socnetwork.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.connection.DBConnectionPool;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.dao.sql.*;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Singleton DaoFactory class for Dao layer
 *
 */
public class DaoFactory {
    private static final Logger log = LoggerFactory.getLogger(DaoFactory.class);
    private static volatile DaoFactory daoINSTANCE;
    private final String CONNJNDI = "JNDI";
    private final String CONNPOOL = "CONNPOLL";
    private final String CONNSINGE = "CONNSINGE";

    private String dbConnectionType;
    private String dbType;
    private Boolean initSQLFiles;
    private String dbuser;
    private String dbpassword;
    private String dburl;
    private String dbdriver;
    private String dbpoolsize;

    // TomCat JNDI DataSource does not work here
    // @Resource(name = "jdbc/TestDB")
    private DataSource dataSource;

    private DaoFactory(){
        try {
            loadProperties();
            if(!dbConnectionType.equals(CONNJNDI)){
                // если соединение connection pool либо одиночное регистрируем драйвер бд
                Class.forName(dbdriver);
            }
        }catch (IOException | ClassNotFoundException e){
            log.error("Critical error initialization dao layer connection method", e);
        }
        
    }

    private void loadProperties() throws IOException {
        Properties properties = new Properties();
        try {
            properties.load(DaoFactory.class.getResourceAsStream("/app.properties"));

            dbConnectionType = properties.getProperty("db.connection").toUpperCase();
            dbType = properties.getProperty("db.type").toUpperCase();
            initSQLFiles = Boolean.parseBoolean(properties.getProperty("db.initsqlfiles"));

            switch (dbType){
                case  "H2DB":
                    dbdriver = properties.getProperty("db.h2db.driver");
                    dburl = properties.getProperty("db.h2db.url");
                    dbuser = properties.getProperty("db.h2db.user");
                    dbpassword = properties.getProperty("db.h2db.password");
                    dbpoolsize= properties.getProperty("db.h2db.poolSize");
                    break;
                case "MYSQL":
                    dbdriver = properties.getProperty("db.mysql.driver");
                    dburl = properties.getProperty("db.mysql.url");
                    dbuser = properties.getProperty("db.mysql.user");
                    dbpassword = properties.getProperty("db.mysql.password");
                    dbpoolsize= properties.getProperty("db.mysql.poolSize");
            }
        } catch (IOException e) {
            throw new IOException("Error read app.properties file", e);
        }
    }

    public static DaoFactory getInstance(){
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
        if (initSQLFiles){
            switch (dbType){
                case "H2DB":
                    initH2Db(resourcesDbProperties);
                    break;
                case  "MYSQL":
                    break;
            }
        }
    }

    public Connection getConnection() throws DAOException{
        log.trace("DaoFactory get connection");

        switch (dbConnectionType){
            case CONNJNDI:
                try {
                    return dataSource.getConnection();
                }catch (SQLException e){
                    log.error("Connection error. No connection to DB", e);
                    throw new DAOException("Connection error. No connection to DB", e);
                }
            case CONNPOOL:
                // TODO: 18.03.2017 AddConnectionPool
                return getPooledConnection();
            case CONNSINGE:
                return getSingleConnection();
        }
        return getSingleConnection();
    }

    public Connection getSingleConnection() throws DAOException{
        log.trace("JDBC Driver manager get connection");
        try {
            return DriverManager.getConnection(dburl, dbuser, dbpassword);
        } catch (SQLException e) {
            throw new DAOException("No single JDBC connection", e);
        }
    }

    private Connection getPooledConnection() throws DAOException{
        log.trace("Connection pool get connection");
        return DBConnectionPool.getInstance(Integer.valueOf(dbpoolsize)).getConnection();
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

    public UserDao getUserDao() {
        switch (dbType) {
            case "MYSQL":
            case "H2DB":
            default: return new SQLUserDao();
        }
    }

    public FriendsDao getFriendsDao() {
        switch (dbType) {
            case "MYSQL":
            case "H2DB":
            default: return new SQLFriendsDao();
        }
    }

    public MessageDao getMessageDao() {
        switch (dbType) {
            case "MYSQL":
            case "H2DB":
            default: return new SQLMessageDao();
        }
    }

    public PostDao getPostDao() {
        switch (dbType) {
            case "MYSQL":
            case "H2DB":
            default: return new SQLPostDao();
        }
    }

    public CarsDao getCarsDao() {
        switch (dbType) {
            case "MYSQL":
            case "H2DB":
            default: return new SQLCarsDao();
        }
    }

    public ModelDao getModelDao() {
        switch (dbType) {
            case "MYSQL":
            case "H2DB":
            default: return new SQLModelDao();
        }
    }

}
