package manga;

import java.io.Serializable;
import java.util.List;

public class Manga implements Serializable{
    private static final long serialVersionUID = 1L;

    private String isbn;
    private String title;
    private List<String> authors;
    private int startYear;
    private int endYear;
    private String publisher;
    private String genre;
    private String magazine;
    private int editionYear;
    private int totalVolumes;
    private int acquiredVolumesCounter;
    private List<Integer> acquiredVolumes;	

    public Manga(String isbn, String title, List<String> authors, int startYear, int endYear, String publisher, String genre, String magazine, int editionYear, int totalVolumes, int acquiredVolumesCounter, List<Integer> acquiredVolumes) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.startYear = startYear;
        this.endYear = endYear;
        this.publisher = publisher;
        this.genre = genre;
        this.magazine = magazine;
        this.editionYear = editionYear;
        this.totalVolumes = totalVolumes;
        this.acquiredVolumesCounter = acquiredVolumesCounter;
        this.acquiredVolumes = acquiredVolumes;
    }
    
    @Override
    public String toString() {
        return "Manga : " + title + "\n" + "ISBN : " + isbn + "\n" + "Authors : " + authors + "\n" + "Start Year : " + startYear + "\n" + "End Year : " + endYear + "\n" + "Publisher : " + publisher + "\n" + "Genre : " + genre + "\n" + "Magazine : " + magazine + "\n" + "Edition Year : " + editionYear + "\n" + "Total Volumes : " + totalVolumes + "\n" + "Acquired Volumes : " + acquiredVolumes + "\n";
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public String getGenre() {
        return genre;
    }

    public String getMagazine() {
        return magazine;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getEditionYear() {
        return editionYear;
    }

    public int getTotalVolumes() {
        return totalVolumes;
    }

    public int getAcquiredVolumesCount() {
        return acquiredVolumesCounter;
    }

    public List<Integer> getAcquiredVolumes() {
        return acquiredVolumes;
    }

}