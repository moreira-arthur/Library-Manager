package manga;

public class IndexEntry {
    private String isbn;
    private long filePointer;

    public IndexEntry(String isbn, long filePointer) {
        this.isbn = isbn;
        this.filePointer = filePointer;
    }

    public String getIsbn() {
        return isbn;
    }

    public long getFilePointer() {
        return filePointer;
    }
}
