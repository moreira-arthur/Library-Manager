package manga;

/**
 * TitleIndexEntry class
 * It represents a title index entry object with its attributes
 * - title: Title
 * - isbn: International Standard Book Number
 */
public class TitleIndexEntry {
    private final String title;
    private final String isbn;

    /**
     * Constructor
     * @param title Title
     * @param isbn ISBN
     */
    public TitleIndexEntry(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    /**
     * Get the title
     * @return Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the ISBN
     * @return ISBN
     */
    public String getIsbn() {
        return isbn;
    }
}
