package externalTools;

public class ConnectionException extends Exception {
    private ConnectionException(String message) {
        super(message);
    }

    public static ConnectionException databaseNameIsNullException() {
        return new ConnectionException("Database name is null!");
    }

    public static ConnectionException userIsNullException() {
        return new ConnectionException("User is null!");
    }

    public static ConnectionException passwordIsNullException() {
        return new ConnectionException("Password is null!");
    }
}
