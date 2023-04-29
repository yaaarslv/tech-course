package tools;

public class CatException extends Exception{
    private CatException(String message) {
        super(message);
    }

    public static CatException nameIsNullException() {
        return new CatException("Name is null!");
    }

    public static CatException breedIsNullException() {
        return new CatException("Breed is null!");
    }

    public static CatException catIsNullException() {
        return new CatException("Cat is null!");
    }
}
