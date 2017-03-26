package ru.pharus.socnetwork.dao.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.MessageDao;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLMessageDao implements MessageDao {
    private static final Logger log = LoggerFactory.getLogger(SQLMessageDao.class);
    private DaoFactory factory = DaoFactory.getInstance();

    @Override
    public int create(Message message) throws DAOException {
        if (message == null) {
            log.warn("Method create got NullPointer Message entity");
            return 0;
        }

        log.debug(String.format("Creating new message %s" , message.getFromUserId()));

        String sql = "INSERT INTO messages(from_user_id, to_user_id, message, post_time) VALUES (?,?,?,?)";
        try (Connection conn = factory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setObject(1, message.getFromUserId());
            statement.setObject(2, message.getToUserId());
            statement.setObject(3, message.getMessage());
            statement.setObject(4, message.getPostTime());

            log.trace("Open connection and statement. Execute query: create message");
            statement.executeUpdate();
            message.setId(statement.getGeneratedKeys().getInt(1));

            return message.getId();

        } catch (SQLException e) {
            log.warn(String.format("SQL error: cannot add message from user %s", message.getFromUserId()), e);
            throw new DAOException("SQL error: cannot add message from user", e);
        }
    }

    @Override
    public List<Message> getMessagesByCriteria(String where) throws DAOException {
        log.debug(String.format("Get messages by criteria %s", where));

        String sql = "SELECT id, from_user_id, to_user_id, message, post_time  FROM messages " + where;

        try(Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){

            List<Message> list = new ArrayList<>();

            while (resultSet.next()){
                list.add(new Message(
                        resultSet.getInt("id"),
                        resultSet.getInt("from_user_id"),
                        resultSet.getInt("to_user_id"),
                        resultSet.getString("message"),
                        resultSet.getTimestamp("post_time").toLocalDateTime()
                        ));
            }

            log.debug(String.format("Done. Close connection. Return %d messages", list.size()));
            return list;
        } catch (SQLException e) {
            log.error("SQL error: select from messages table");
            throw  new DAOException("SQL error: select from messages table", e);
        }
    }
}
