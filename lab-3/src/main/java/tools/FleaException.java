package tools;

public class FleaException extends Exception{
    private FleaException(String message) {
        super(message);
    }

    public static FleaException nameIsNullException() {
        return new FleaException("Name is null!");
    }

    public static FleaException fleaIsNullException() {
        return new FleaException("Cat is null!");
    }
}
