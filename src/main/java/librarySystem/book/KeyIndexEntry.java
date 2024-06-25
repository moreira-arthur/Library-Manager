package librarySystem.book;

/**
 * TitleIndexEntry class
 * It represents a title index entry object with its attributes
 * - key: Title, author, or genre
 * - isbn: International Standard Book Number
 */
public class KeyIndexEntry {
    private final String key;
    private final String isbn;

    /**
     * Constructor
     * @param key key
     * @param isbn ISBN
     */
    public KeyIndexEntry(String key, String isbn) {
        this.key = key;
        this.isbn = isbn;
    }

    /**
     * Get the title
     * @return Title
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the ISBN
     * @return ISBN
     */
    public String getIsbn() {
        return isbn;
    }
}
