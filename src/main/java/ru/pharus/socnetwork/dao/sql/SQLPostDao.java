package ru.pharus.socnetwork.dao.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.dao.PostDao;
import ru.pharus.socnetwork.dao.exception.DAOException;
import ru.pharus.socnetwork.entity.Post;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLPostDao implements PostDao{
    private static final Logger log = LoggerFactory.getLogger(SQLPostDao.class);
    private DaoFactory factory = DaoFactory.getInstance();

    @Override
    public int create(Post message) throws DAOException {
        if (message == null) {
            log.warn("Method create got NullPointer car Model entity");
            return 0;
        }

        log.debug(String.format("Creating new post %s" ,message.getTitle()));

        String sql = "INSERT INTO posts(user_id, title, post) VALUES (?,?,?)";
        try (Connection conn = factory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setObject(1, message.getUserId());
            statement.setObject(2, message.getTitle());
            statement.setObject(3, message.getText());

            log.trace("Open connection and statement. Execute query: insert post");
            statement.executeUpdate();

            try(ResultSet rs = statement.getGeneratedKeys()){
                if(rs.next()) message.setId(rs.getInt(1));
            }
            return message.getId();
        } catch (SQLException e) {
            log.warn(String.format("SQL error: cannot add post %s", message.getTitle()), e);
            throw new DAOException("SQL error: cannot add post ", e);
        }
    }

    @Override
    public void update(Post message) throws DAOException {
        if (message == null || message.getId() == 0) {
            log.warn("Method update got NullPointer user Post entity");
            return;
        }

        log.debug(String.format("Updating user post with id", message.getId()));

        String sql = "UPDATE posts set title = ?, post = ? WHERE id = " + message.getId();

        try (Connection conn = factory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setObject(1, message.getTitle());
            statement.setObject(2, message.getText());
            log.trace("Open connection and statement. Execute query: update user post");
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn("SQL error: cannot to update user post", e);
            throw new DAOException("SQL error: cannot to update user post", e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        if (id < 1) return;

        log.debug(String.format("deleting user post %d", id));

        String sql = "Delete from posts where id=" + id;

        try(Connection conn = factory.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn(String.format("SQL error: cannot to delete user post %s", id), e);
            throw new DAOException("SQL error: cannot to delete user post", e);
        }
    }

    @Override
    public Post getById(int id) throws DAOException {
        return getByCriteria(" where id="+id).stream().findFirst().orElse(null);
    }

    @Override
    public List<Post> getByCriteria(String where) throws DAOException {
        log.debug(String.format("Get user posts by criteria %s", where));
        List<Post> list = new ArrayList<>();

        String sql = String.format("SELECT posts.id, user_id, title, post FROM posts %s ORDER BY id DESC", where);

        try (Connection conn = factory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            log.trace("Open connection and statement. Execute query: select posts");

            while (resultSet.next()) {
                Post post = new Post(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("title"),
                        resultSet.getString("post")
                );
                list.add(post);
            }

            log.debug(String.format("Done. Close connection. Return %d user posts", list.size()));
            return list;

        } catch (SQLException e) {
            log.warn("SQL error: cannot select car models", e);
            throw new DAOException("SQL error: cannot select car models", e);
        }
    }

    @Override
    public List<Post> getUserPost(int userId) throws DAOException {
        return getByCriteria(" where user_id=" + userId);
    }
}
