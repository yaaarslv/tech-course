package banks.Tools;

/**
 * An exception that can occur in relation to a bank client.
 */
public class ClientException extends Exception {
    /**
     * Constructs a new ClientException with the given message.
     * @param message the error message
     */
    private ClientException(String message) {
        super(message);
    }

    /**
     * Returns a ClientException indicating that the client is null.
     * @return a ClientException indicating that the client is null
     */
    public static ClientException clientIsNullException() {
        return new ClientException("Client is null!");
    }

    /**
     * Returns a ClientException indicating that the client name is null.
     * @return a ClientException indicating that the client name is null
     */
    public static ClientException clientNameIsNullException() {
        return new ClientException("Client name is null!");
    }

    /**
     * Returns a ClientException indicating that the client already exists in the list.
     * @return a ClientException indicating that the client already exists in the list
     */
    public static ClientException clientAlreadyExistsException() {
        return new ClientException("Client already exists in list!");
    }

    /**
     * Returns a ClientException indicating that the client doesn't exist in the list of bank clients.
     * @return a ClientException indicating that the client doesn't exist in the list of bank clients
     */
    public static ClientException clientNotExistsException() {
        return new ClientException("Client doesn't exist in list of bank clients!");
    }

    /**
     * Returns a ClientException indicating that the name isn't full.
     * @return a ClientException indicating that the name isn't full
     */
    public static ClientException nameIsNotFullException() {
        return new ClientException("Name isn't full!");
    }
}
