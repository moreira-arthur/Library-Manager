package manga;

import java.util.List;

/**
 * Manga class
 * It represents a manga object with its attributes
 * - isbn: International Standard Book Number
 * - title: Title of the manga
 * - authors: List of authors
 * - startYear: Year of the first volume
 * - endYear: Year of the last volume
 * - publisher: Publisher of the manga
 * - genre: Genre of the manga
 * - magazine: Magazine where the manga was published
 * - editionYear: Year of the edition
 * - totalVolumes: Total number of volumes
 * - acquiredVolumesCounter: Number of volumes acquired
 * - acquiredVolumes: List of acquired volumes
 */
public class Manga {

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


    /**
     * Constructor
     * @param isbn ISBN
     * @param title Title
     * @param authors Authors
     * @param startYear Start year
     * @param endYear End year
     * @param publisher Publisher
     * @param genre Genre
     * @param magazine Magazine
     * @param editionYear Edition year
     * @param totalVolumes Total volumes
     * @param acquiredVolumesCounter Acquired volumes counter
     * @param acquiredVolumes Acquired volumes
     */
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
        return "Manga : " + title + "\n"
                + "ISBN : " + isbn + "\n" 
                + "Authors : " + authors + "\n" 
                + "Start Year : " + startYear + "\n" 
                + "End Year : " + endYear + "\n" 
                + "Publisher : " + publisher + "\n" 
                + "Genre : " + genre + "\n" 
                + "Magazine : " + magazine + "\n" 
                + "Edition Year : " + editionYear + "\n" 
                + "Total Volumes : " + totalVolumes + "\n" 
                + "Amount of acquired volumes: "  + acquiredVolumesCounter  + "\n"
                + "Acquired Volumes : " + acquiredVolumes + "\n";
    }

    // Getters

    /**
     * Get the ISBN of the manga
     * @return ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Get the title of the manga
     * @return Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the authors of the manga
     * @return Authors
     */
    public List<String> getAuthors() {
        return authors;
    }

    /**
     * Get the start year of the manga
     * @return Start year
     */
    public int getStartYear() {
        return startYear;
    }

    /**
     * Get the end year of the manga
     * @return End year
     */
    public int getEndYear() {
        return endYear;
    }

    /**
     * Get the genre of the manga
     * @return Genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Get the magazine of the manga
     * @return Magazine
     */
    public String getMagazine() {
        return magazine;
    }

    /**
     * Get the publisher of the manga
     * @return Publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Get the edition year of the manga
     * @return Edition year
     */
    public int getEditionYear() {
        return editionYear;
    }

    /**
     * Get the total number of volumes of the manga
     * @return Total volumes
     */
    public int getTotalVolumes() {
        return totalVolumes;
    }

    /**
     * Get the number of acquired volumes of the manga
     * @return Acquired volumes counter
     */
    public int getAcquiredVolumesCounter() {
        return acquiredVolumesCounter;
    }

    /**
     * Get the list of acquired volumes of the manga
     * @return Acquired volumes
     */
    public List<Integer> getAcquiredVolumes() {
        return acquiredVolumes;
    }

}