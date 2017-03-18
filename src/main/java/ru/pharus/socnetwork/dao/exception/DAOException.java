package ru.pharus.socnetwork.dao.exception;

/** Default exception for DAO layer
 */
public class DAOException extends Exception {
    public DAOException() {
        super();
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(String message) {
        super(message);
    }
}
