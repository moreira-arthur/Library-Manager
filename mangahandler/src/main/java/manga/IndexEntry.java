package manga;

/**
 * IndexEntry class
 * It represents an index entry object with its attributes
 * - isbn: International Standard Book Number
 * - filePointer: File pointer
 */
public class IndexEntry {
    private final  String isbn;
    private final  long filePointer;

    /**
     * Constructor
     * @param isbn ISBN
     * @param filePointer File pointer
     */
    public IndexEntry(String isbn, long filePointer) {
        this.isbn = isbn;
        this.filePointer = filePointer;
    }

    /**
     * Get the ISBN
     * @return ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Get the file pointer
     * @return File pointer
     */
    public long getFilePointer() {
        return filePointer;
    }
}
