package librarySystem.book;

import java.io.*;
import java.util.*;

/**
 * Handles the CRUD operations for the book class.
    * The data is stored in a binary file named "book.dat" with a fixed record size of 2048 bytes.
    * The index is stored in a binary file named "index.dat" with a variable record size.
    * The title index is stored in a binary file named "title_index.dat" with a variable record size.
    * The deleted records spaces are stored in a list.
 */
public class BookHandler {


    private static final String DATA_FILE = "book.dat";
    private static final String INDEX_FILE = "index.dat";
    private static final String TEMP_FILE = "temp.dat";
    private static final String TITLE_INDEX_FILE = "title_index.dat";
    private static final String AUTHOR_INDEX_FILE = "author_index.dat";
    private static final String GENRE_INDEX_FILE = "genre_index.dat";
    private static final int RECORD_SIZE = 2048;
    private final List<Long> deletedRecordsSpaces;
    private SecondayIndexManager authorIndexManager;
    private SecondayIndexManager genreIndexManager;
    public SecondayIndexManager titleIndexManager;

    private static final int NOT_FOUND = -1;

    /**
     * Creates a new BookHandler object.
     * The constructor initializes the list of deleted records spaces by calling the loadDeletedRecordsSpaces method.
     */
    public BookHandler() {
        deletedRecordsSpaces = new ArrayList<>();
        titleIndexManager = new SecondayIndexManager(TITLE_INDEX_FILE);
        authorIndexManager = new SecondayIndexManager(AUTHOR_INDEX_FILE);
        genreIndexManager = new SecondayIndexManager(GENRE_INDEX_FILE);
        loadDeletedRecordsSpaces();
    }

    /**
     * Loads the deleted records spaces from the data file.
     * The method reads the data file and adds the file pointers of the deleted records to the deletedRecordsSpaces list.
     */
    private void loadDeletedRecordsSpaces(){

        char status;

        try (RandomAccessFile dataFile = new RandomAccessFile(DATA_FILE, "rw")){
            long pointer = 0;
            while (pointer < dataFile.length()) {
                dataFile.seek(pointer);
                status = dataFile.readChar();
                if (status == '*') {
                    deletedRecordsSpaces.add(pointer);
                }
                pointer += RECORD_SIZE;
            }
        } catch (IOException e) {
            System.out.println("Error in loadDeletedRecordsSpaces : " + e.getMessage());
        }
    }


    /**
     * Adds a new book to the data file.
     * The method writes the book object to the data file and adds an index entry to the index file.
     * The method also adds a title index entry to the title index file.
     * @param book The book object to be added.
     * @throws IOException If an I/O error occurs.
     */
    public void addBook(Book book) throws IOException {

        long filePointer;

        if(getPositionFromIndexFile(book.getIsbn()) != NOT_FOUND){
            throw new IOException("book already exists");
        }

        if (!deletedRecordsSpaces.isEmpty()) {
            filePointer = deletedRecordsSpaces.remove(0);
        } else {
            try (RandomAccessFile dataFile = new RandomAccessFile(DATA_FILE, "rw")) {
                    filePointer = dataFile.length();
            }catch(FileNotFoundException e){
                System.out.println("File not found, creating new file...");
                filePointer = 0;
            }
        }

        try (RandomAccessFile dataFile = new RandomAccessFile(DATA_FILE, "rw")) {
            dataFile.seek(filePointer);
            writeBook(dataFile, book);
            addIndex(book.getIsbn(), filePointer);
            titleIndexManager.addSecondaryIndex(book.getTitle(), book.getIsbn());
            authorIndexManager.addSecondaryIndex(book.getAuthor(), book.getIsbn());
            genreIndexManager.addSecondaryIndex(book.getGenre(), book.getIsbn());
        }catch(FileNotFoundException e){
            System.out.println("FIle not found, creating new file...");
        }
    }

    /**
     * Retrieves a book from the data file by its ISBN.
     * The method reads the data file using the file pointer from the index file.
     * @param isbn The ISBN of the book to retrieve.
     * @return The book object with the specified ISBN, or null if not found.
     * @throws IOException If an I/O error occurs.
     */
    public Book getBook(String isbn) throws IOException{

        long filePointer = getPositionFromIndexFile(isbn);

        if (filePointer == NOT_FOUND){
            System.out.println("book not found");
            return null;
        }
        try (RandomAccessFile dataFile = new RandomAccessFile(DATA_FILE, "r")) {
            dataFile.seek(filePointer);
            return readBook(dataFile);
        }catch (FileNotFoundException e){
            System.out.println("File not found");
            return null;
        }
    }

    /**
     * Updates a book in the data file by its ISBN.
     * The method reads the data file using the file pointer from the index file and updates the book object.
     * @param isbn The ISBN of the book to update.
     * @param updatedBook The updated book object.
     * @throws IOException If an I/O error occurs.
     */
    public void updateBook(String isbn, Book updatedBook) throws IOException {

        long filePointer = getPositionFromIndexFile(isbn);

        if (filePointer == NOT_FOUND){
            throw new IOException("book not found");
        }
        try (RandomAccessFile dataFile = new RandomAccessFile(DATA_FILE, "rw")) {
            dataFile.seek(filePointer);
            // Check if the title has changed and update the title index
            Book tempBook = getBook(isbn);
            if(!tempBook.getTitle().equals(updatedBook.getTitle())){
                titleIndexManager.removeSecondaryIndex(isbn);
                titleIndexManager.addSecondaryIndex(updatedBook.getTitle(), isbn);
                }
            writeBook(dataFile, updatedBook);
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }
    }

    /**
     * Deletes a book from the data file by its ISBN.
     * The method reads the data file using the file pointer from the index file and marks the record as deleted.
     * The method also removes the index entry and the title index entry.
     * @param isbn The ISBN of the book to delete.
     * @throws IOException If an I/O error occurs.
     */
    public void deleteBook(String isbn) throws IOException {

        long filePointer = getPositionFromIndexFile(isbn);

        if (filePointer == NOT_FOUND){
            throw new IOException("book not found");
        }

        try (RandomAccessFile dataFile = new RandomAccessFile(DATA_FILE, "rw")) {
            dataFile.seek(filePointer);
            //Mark register as deleted
            dataFile.writeChar('*');
            deletedRecordsSpaces.add(filePointer);
        }
        removeIndex(isbn);
        titleIndexManager.removeSecondaryIndex(isbn);
        authorIndexManager.removeSecondaryIndex(isbn);
        genreIndexManager.removeSecondaryIndex(isbn);
    }


    /**
     * Searches for books by title.
     * The method searches the title index file for all titles that match the specified title.
     * For each title found, the method retrieves the ISBNs from the title index file and retrieves the books from the data file.
     * @param title The title to search for.
     * @return A list of book objects with titles that match the specified title.
     * @throws IOException If an I/O error occurs.
     */
    public List<Book> searchBooksByTitle(String title) throws IOException {

        List<String> isbns = titleIndexManager.getIsbnsByKey(title);
        List<Book> books = new ArrayList<>();
        Book book;

        for (String isbn : isbns) {
            book = getBook(isbn);
            if (book != null) {
                books.add(book);
            }
        }
        return books;
    }

    /**
     * Searches for books by author.
     * The method searches the author index file for all authors that match the specified author.
     * For each author found, the method retrieves the ISBNs from the author index file and retrieves the books from the data file.
     * @param author The author to search for.
     * @return A list of book objects with authors that match the specified author.
     * @throws IOException If an I/O error occurs.
     */
    public List<Book> searchBooksByAuthor(String author) throws IOException {

        List<String> isbns = authorIndexManager.getIsbnsByKey(author);
        List<Book> books = new ArrayList<>();
        Book book;

        for (String isbn : isbns) {
            book = getBook(isbn);
            if (book != null) {
                books.add(book);
            }
        }
        return books;
    }

    /**
     * Searches for books by genre.
     * The method searches the genre index file for all genres that match the specified genre.
     * For each genre found, the method retrieves the ISBNs from the genre index file and retrieves the books from the data file.
     * @param genre The genre to search for.
     * @return A list of book objects with genres that match the specified genre.
     * @throws IOException If an I/O error occurs.
     */
    public List<Book> searchBooksByGenre(String genre) throws IOException {

        List<String> isbns = genreIndexManager.getIsbnsByKey(genre);
        List<Book> books = new ArrayList<>();
        Book book;

        for (String isbn : isbns) {
            book = getBook(isbn);
            if (book != null) {
                books.add(book);
            }
        }
        return books;
    }





    /**
     * Searches for a book by its ISBN.
     * The method retrieves the book from the data file using the getManga method.
     * @param isbn The ISBN of the book to search for.
     * @return A list with the book object with the specified ISBN, or an empty list if not found.
     * @throws IOException If an I/O error occurs.
     */
    public List<Book> searchBookByIsbn(String isbn) throws IOException {

        List<Book> books = new ArrayList<>();
        Book book = getBook(isbn);

        if (book != null) {
            books.add(book);
        }
        return books;
    }

    /**
     * Writes a book object to the data file.
     * The method writes the book object to the data file using the specified record size.
     * The method fills the remaining space with blank bytes to complete the record size.
     * @param data_file The RandomAccessFile object for the data file.
     * @param book The book object to write.
     * @throws IOException If an I/O error occurs.
     */
    private void writeBook(RandomAccessFile data_file, Book book) throws IOException {

        long start_Pointer = data_file.getFilePointer();
        data_file.writeUTF(book.getIsbn());
        data_file.writeUTF(book.getTitle());
        data_file.writeUTF(book.getAuthor());
        data_file.writeInt(book.getStartYear());
        data_file.writeInt(book.getEndYear());
        data_file.writeUTF(book.getGenre());
        data_file.writeUTF(book.getMagazine());
        data_file.writeUTF(book.getPublisher());
        data_file.writeInt(book.getEditionYear());
        data_file.writeInt(book.getTotalVolumes());
        data_file.writeInt(book.getAcquiredVolumesCounter());

        for (Integer volume : book.getAcquiredVolumes()) {
            data_file.writeInt(volume);
        }

        long end_Pointer = data_file.getFilePointer();

        // Fill the remaining space with blank bytes to complete the record size
        long remainingBytes = RECORD_SIZE - (end_Pointer - start_Pointer);
        if (remainingBytes > 0) {
            data_file.write(new byte[(int) remainingBytes]);
        }
    }

    /**
     * Reads a book object from the data file.
     * The method reads the book object from the data file using the specified record size.
     * @param file The RandomAccessFile object for the data file.
     * @return The book object read from the data file.
     * @throws IOException If an I/O error occurs.
     */
    private Book readBook(RandomAccessFile file) throws IOException {

        String isbn = file.readUTF();
        String title = file.readUTF();
        String author = file.readUTF();
        int startYear = file.readInt();
        int endYear = file.readInt();
        String genre = file.readUTF();
        String magazine = file.readUTF();
        String publisher = file.readUTF();
        int editionYear = file.readInt();
        int totalVolumes = file.readInt();
        int acquiredVolumesCount = file.readInt();

        List<Integer> acquiredVolumes = new ArrayList<>();
        for (int i = 0; i < acquiredVolumesCount; i++) {
            acquiredVolumes.add(file.readInt());
        }

        file.seek(file.getFilePointer() + (RECORD_SIZE - (file.getFilePointer() % RECORD_SIZE)));
        return new Book(isbn, title, author, startYear, endYear, genre, magazine, publisher, editionYear, totalVolumes, acquiredVolumesCount, acquiredVolumes);
    }

    /**
     * Adds an index entry to the index file.
     * The method reads the current index entries from the index file and adds the new index entry.
     * The method sorts the index entries by ISBN and writes them back to the index file.
     * @param isbn The ISBN of the  to add to the index file.
     * @param filePointer The file pointer of the  in the data file.
     * @throws IOException If an I/O error occurs.
     */
    private void addIndex(String isbn, long filePointer) throws IOException {

        List<IndexEntry> indexEntries = new ArrayList<>();
        String currentIsbn;
        long currentPointer;

        // Read all actual index
        try (RandomAccessFile indexFile = new RandomAccessFile(INDEX_FILE, "r")) {
            while (indexFile.getFilePointer() < indexFile.length()) {
                currentIsbn = indexFile.readUTF();
                currentPointer = indexFile.readLong();
                indexEntries.add(new IndexEntry(currentIsbn, currentPointer));
            }
        }catch(FileNotFoundException e){
            System.out.println("File not found, creating new file...");
        }catch(IOException e){
            System.out.println("I/O Error in addIndex method");
        }catch(Exception e){
            System.out.println("Error in addIndex method");
        }

        indexEntries.add(new IndexEntry(isbn, filePointer));

        indexEntries.sort(Comparator.comparing(IndexEntry::getIsbn));

        try (RandomAccessFile indexFile = new RandomAccessFile(INDEX_FILE, "rw")) {
            indexFile.setLength(0); //Clear the file
            for (IndexEntry entry : indexEntries) {
                indexFile.writeUTF(entry.getIsbn());
                indexFile.writeLong(entry.getFilePointer());
            }
        }
    }

    /**
     * Retrieves the file pointer of a  in the data file by its ISBN.
     * The method reads the index file and uses binary search to find the index entry with the specified ISBN.
     * @param isbn The ISBN of the  to find.
     * @return The file pointer of the book in the data file, or -1 if not found.
     * @throws IOException If an I/O error occurs.
     */
    private long getPositionFromIndexFile(String isbn) throws IOException {

        List<IndexEntry> indexEntries = new ArrayList<>();
        String currentIsbn;
        long filePointer;

        // Read all current indexes
        try (RandomAccessFile indexFile = new RandomAccessFile(INDEX_FILE, "r")) {
            while (indexFile.getFilePointer() < indexFile.length()) {
                currentIsbn = indexFile.readUTF();
                filePointer = indexFile.readLong();
                indexEntries.add(new IndexEntry(currentIsbn, filePointer));
            }
        }catch(FileNotFoundException e){
            System.out.println("File not found in getPositionFromIndexFile method");
        }

        // Use binary search to find the index
        IndexEntry midEntry;
        int mid;
        int cmp;
        int left = 0;
        int right = indexEntries.size() - 1;

        while (left <= right) {
            mid = (left + right) / 2;
            midEntry = indexEntries.get(mid);
            cmp = midEntry.getIsbn().compareTo(isbn);
            if (cmp < 0) {
                left = mid + 1;
            } else if (cmp > 0) {
                right = mid - 1;
            } else {
                return midEntry.getFilePointer();
            }
        }
        return NOT_FOUND;
    }

    /**
     * Removes an index entry from the index file.
     * The method reads the current index entries from the index file and writes them back to a temporary file.
     * The method skips the index entry with the specified ISBN and renames the temporary file to the index file.
     * @param isbn_rem The ISBN of the book to remove from the index file.
     * @throws IOException If an I/O error occurs.
     */
    private void removeIndex(String isbn_rem) throws IOException {

        File tempFile = new File(TEMP_FILE);
        String currentIsbn;
        long filePointer;

        try (RandomAccessFile indexFile = new RandomAccessFile(INDEX_FILE, "rw");
            RandomAccessFile tempIndexFile = new RandomAccessFile(tempFile, "rw")) {
            // Besides storing the IndexEntry in a list, we write it to a temporary file to avoid reading the whole index file again
            while (indexFile.getFilePointer() < indexFile.length()) {
                currentIsbn = indexFile.readUTF();
                filePointer = indexFile.readLong();
                if (!currentIsbn.equals(isbn_rem)) {
                    tempIndexFile.writeUTF(currentIsbn);
                    tempIndexFile.writeLong(filePointer);
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("File not found in removeIndex method");
        }
        tempFile.renameTo(new File(INDEX_FILE));
    }

    public List<String> getAllBooksTitles() throws IOException {
        List<String> titles = new ArrayList<>();
        String title;

        try (RandomAccessFile indexFile = new RandomAccessFile(TITLE_INDEX_FILE, "r")) {

            indexFile.seek(0);
            while (indexFile.getFilePointer() < indexFile.length()) {
                title = indexFile.readUTF();
                indexFile.readUTF(); // Skip the ISBN
                titles.add(title);
            }
        }

        return titles;
    }
}