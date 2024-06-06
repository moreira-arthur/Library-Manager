package manga;

public class TitleIndexEntry {
    private String title;
    private String isbn;

    public TitleIndexEntry(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }
}
