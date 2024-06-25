package librarySystem.book;

import java.util.List;

/**
 * book class
 * It represents a book object with its attributes
 * - isbn: International Standard Book Number
 * - title: Title of the book
 * - authors: List of authors
 * - startYear: Year of the first volume
 * - endYear: Year of the last volume
 * - publisher: Publisher of the book
 * - genre: Genre of the book
 * - magazine: Magazine where the book was published
 * - editionYear: Year of the edition
 * - totalVolumes: Total number of volumes
 * - acquiredVolumesCounter: Number of volumes acquired
 * - acquiredVolumes: List of acquired volumes
 */
public class Book {

    private final String isbn;
    private final String title;
    private final String author;
    private final int startYear;
    private final int endYear;
    private final String publisher;
    private final String genre;
    private final String magazine;
    private final int editionYear;
    private final int totalVolumes;
    private final int acquiredVolumesCounter;
    private final List<Integer> acquiredVolumes;


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
    public Book(String isbn, String title, String author, int startYear, int endYear,  String genre, String magazine,String publisher, int editionYear, int totalVolumes, int acquiredVolumesCounter, List<Integer> acquiredVolumes) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.startYear = startYear;
        this.endYear = endYear;
        this.genre = genre;
        this.magazine = magazine;
        this.publisher = publisher;
        this.editionYear = editionYear;
        this.totalVolumes = totalVolumes;
        this.acquiredVolumesCounter = acquiredVolumesCounter;
        this.acquiredVolumes = acquiredVolumes;
    }
    
    @Override
    public String toString() {
        return "Book : " + title + "\n"
                + "ISBN : " + isbn + "\n" 
                + "Authors : " + author + "\n" 
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
     * Get the ISBN of the book
     * @return ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Get the title of the book
     * @return Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the authors of the book
     * @return Authors
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Get the start year of the book
     * @return Start year
     */
    public int getStartYear() {
        return startYear;
    }

    /**
     * Get the end year of the book
     * @return End year
     */
    public int getEndYear() {
        return endYear;
    }

    /**
     * Get the genre of the book
     * @return Genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Get the magazine of the book
     * @return Magazine
     */
    public String getMagazine() {
        return magazine;
    }

    /**
     * Get the publisher of the book
     * @return Publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Get the edition year of the book
     * @return Edition year
     */
    public int getEditionYear() {
        return editionYear;
    }

    /**
     * Get the total number of volumes of the book
     * @return Total volumes
     */
    public int getTotalVolumes() {
        return totalVolumes;
    }

    /**
     * Get the number of acquired volumes of the book
     * @return Acquired volumes counter
     */
    public int getAcquiredVolumesCounter() {
        return acquiredVolumesCounter;
    }

    /**
     * Get the list of acquired volumes of the book
     * @return Acquired volumes
     */
    public List<Integer> getAcquiredVolumes() {
        return acquiredVolumes;
    }

}