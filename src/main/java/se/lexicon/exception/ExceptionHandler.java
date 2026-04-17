package se.lexicon.exception;

public class ExceptionHandler {

    public static String handle(Exception e) {
        if (e instanceof IllegalArgumentException) {
            return "Validation Error: " + e.getMessage();
        } else if (e instanceof DuplicateContactException) {
            return "Duplicate Error: " + e.getMessage();
        } else if (e instanceof ContactStorageException) {
            return "Storage Error: " + e.getMessage();
        } else {
            return "Unexpected Error: " + e.getMessage();
        }
    }
}