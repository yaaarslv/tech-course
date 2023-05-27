package ownerTools;

public class OwnerException extends Exception {
    private OwnerException(String message) {
        super(message);
    }

    public static OwnerException nameIsNullException() {
        return new OwnerException("Name is null!");
    }

    public static OwnerException ownerIsNullException() {
        return new OwnerException("Owner is null!");
    }
}
