package manga;

import java.io.*;
import java.util.*;

/**
 * Handles the CRUD operations for the Manga class.
    * The data is stored in a binary file named "manga.dat" with a fixed record size of 2048 bytes.
    * The index is stored in a binary file named "index.dat" with a variable record size.
    * The title index is stored in a binary file named "title_index.dat" with a variable record size.
    * The deleted records spaces are stored in a list.
 */
public class MangaHandler {

    
    private static final String DATA_FILE = "manga.dat";
    private static final String INDEX_FILE = "index.dat";
    private static final String TITLE_INDEX_FILE = "title_index.dat";
    private static final String TEMP_FILE = "temp.dat";
    private static final int RECORD_SIZE = 2048;
    private final List<Long> deletedRecordsSpaces;

    private static final int NOT_FOUND = -1;

    /**
     * Creates a new MangaHandler object.
     * The constructor initializes the list of deleted records spaces by calling the loadDeletedRecordsSpaces method.
     */
    public MangaHandler() {
        deletedRecordsSpaces = new ArrayList<>();
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
     * Adds a new manga to the data file.
     * The method writes the manga object to the data file and adds an index entry to the index file.
     * The method also adds a title index entry to the title index file.
     * @param manga The manga object to be added.
     * @throws IOException If an I/O error occurs.
     */
    public void addManga(Manga manga) throws IOException {

        long filePointer;

        if(getPositionFromIndexFile(manga.getIsbn()) != NOT_FOUND){
            throw new IOException("Manga already exists");
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
            writeManga(dataFile, manga);
            addIndex(manga.getIsbn(), filePointer);
            addTitleIndex(manga.getTitle(), manga.getIsbn());
        }catch(FileNotFoundException e){
            System.out.println("FIle not found, creating new file...");
        }
    }

    /**
     * Retrieves a manga from the data file by its ISBN.
     * The method reads the data file using the file pointer from the index file.
     * @param isbn The ISBN of the manga to retrieve.
     * @return The manga object with the specified ISBN, or null if not found.
     * @throws IOException If an I/O error occurs.
     */
    public Manga getManga(String isbn) throws IOException{

        long filePointer = getPositionFromIndexFile(isbn);

        if (filePointer == NOT_FOUND){
            System.out.println("Manga not found");
            return null;
        }
        try (RandomAccessFile dataFile = new RandomAccessFile(DATA_FILE, "r")) {
            dataFile.seek(filePointer);
            return readManga(dataFile);
        }catch (FileNotFoundException e){
            System.out.println("File not found");
            return null;
        }
    }

    /**
     * Updates a manga in the data file by its ISBN.
     * The method reads the data file using the file pointer from the index file and updates the manga object.
     * @param isbn The ISBN of the manga to update.
     * @param updatedManga The updated manga object.
     * @throws IOException If an I/O error occurs.
     */
    public void updateManga(String isbn, Manga updatedManga) throws IOException {

        long filePointer = getPositionFromIndexFile(isbn);

        if (filePointer == NOT_FOUND){
            throw new IOException("Manga not found");
        }
        try (RandomAccessFile dataFile = new RandomAccessFile(DATA_FILE, "rw")) {
            dataFile.seek(filePointer);
            // Check if the title has changed and update the title index
            Manga tempManga = getManga(isbn);
            if(!tempManga.getTitle().equals(updatedManga.getTitle())){
                removeTitleIndex(isbn);
                addTitleIndex(updatedManga.getTitle(), isbn);
                }
            writeManga(dataFile, updatedManga);
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }
    }

    /**
     * Deletes a manga from the data file by its ISBN.
     * The method reads the data file using the file pointer from the index file and marks the record as deleted.
     * The method also removes the index entry and the title index entry.
     * @param isbn The ISBN of the manga to delete.
     * @throws IOException If an I/O error occurs.
     */
    public void deleteManga(String isbn) throws IOException {

        long filePointer = getPositionFromIndexFile(isbn);

        if (filePointer == NOT_FOUND){
            throw new IOException("Manga not found");
        }
            
        try (RandomAccessFile dataFile = new RandomAccessFile(DATA_FILE, "rw")) {
            dataFile.seek(filePointer);
            //Mark register as deleted
            dataFile.writeChar('*');
            deletedRecordsSpaces.add(filePointer);
        }
        removeIndex(isbn);
        removeTitleIndex(isbn);
    }


    /**
     * Searches for mangas by title.
     * The method searches the title index file for all titles that match the specified title.
     * For each title found, the method retrieves the ISBNs from the title index file and retrieves the mangas from the data file.
     * @param title The title to search for.
     * @return A list of manga objects with titles that match the specified title.
     * @throws IOException If an I/O error occurs.
     */
    public List<Manga> searchMangasByTitle(String title) throws IOException {

        List<String> isbns = getIsbnsByTitle(title);
        List<Manga> mangas = new ArrayList<>();
        Manga manga;

        for (String isbn : isbns) {
            manga = getManga(isbn);
            if (manga != null) {
                mangas.add(manga);
            }
        }
        return mangas;
    }

    /**
     * Searches for a manga by its ISBN.
     * The method retrieves the manga from the data file using the getManga method.
     * @param isbn The ISBN of the manga to search for.
     * @return A list with the manga object with the specified ISBN, or an empty list if not found.
     * @throws IOException If an I/O error occurs.
     */
    public List<Manga> searchMangaByIsbn(String isbn) throws IOException {

        List<Manga> mangas = new ArrayList<>();
        Manga manga = getManga(isbn);

        if (manga != null) {
            mangas.add(manga);
        }
        return mangas;
    }

    /**
     * Writes a manga object to the data file.
     * The method writes the manga object to the data file using the specified record size.
     * The method fills the remaining space with blank bytes to complete the record size.
     * @param data_file The RandomAccessFile object for the data file.
     * @param manga The manga object to write.
     * @throws IOException If an I/O error occurs.
     */
    private void writeManga(RandomAccessFile data_file, Manga manga) throws IOException {

        long start_Pointer = data_file.getFilePointer();
        data_file.writeUTF(manga.getIsbn());
        data_file.writeUTF(manga.getTitle());
        data_file.writeInt(manga.getAuthors().size());

        for (String author : manga.getAuthors()) {
            data_file.writeUTF(author);
        }

        data_file.writeInt(manga.getStartYear());
        data_file.writeInt(manga.getEndYear());
        data_file.writeUTF(manga.getGenre());
        data_file.writeUTF(manga.getMagazine());
        data_file.writeUTF(manga.getPublisher());
        data_file.writeInt(manga.getEditionYear());
        data_file.writeInt(manga.getTotalVolumes());
        data_file.writeInt(manga.getAcquiredVolumesCounter());

        for (Integer volume : manga.getAcquiredVolumes()) {
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
     * Reads a manga object from the data file.
     * The method reads the manga object from the data file using the specified record size.
     * @param file The RandomAccessFile object for the data file.
     * @return The manga object read from the data file.
     * @throws IOException If an I/O error occurs.
     */
    private Manga readManga(RandomAccessFile file) throws IOException {

        String isbn = file.readUTF();
        String title = file.readUTF();
        int authorsCount = file.readInt();

        List<String> authors = new ArrayList<>();
        for (int i = 0; i < authorsCount; i++) {
            authors.add(file.readUTF());
        }

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
        return new Manga(isbn, title, authors, startYear, endYear, genre, magazine, publisher, editionYear, totalVolumes, acquiredVolumesCount, acquiredVolumes);
    }

    /**
     * Adds an index entry to the index file.
     * The method reads the current index entries from the index file and adds the new index entry.
     * The method sorts the index entries by ISBN and writes them back to the index file.
     * @param isbn The ISBN of the manga to add to the index file.
     * @param filePointer The file pointer of the manga in the data file.
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
     * Retrieves the file pointer of a manga in the data file by its ISBN.
     * The method reads the index file and uses binary search to find the index entry with the specified ISBN.
     * @param isbn The ISBN of the manga to find.
     * @return The file pointer of the manga in the data file, or -1 if not found.
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
     * @param isbn_rem The ISBN of the manga to remove from the index file.
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

    /**
     * Adds a title index entry to the title index file.
     * The method reads the current title index entries from the title index file and adds the new title index entry.
     * The method sorts the title index entries by title and writes them back to the title index file.
     * @param title The title of the manga to add to the title index file.
     * @param isbn The ISBN of the manga to add to the title index file.
     * @throws IOException If an I/O error occurs.
     */
    private void addTitleIndex(String title, String isbn) throws IOException {
        String temp_currentTitle;
        String temp_currentIsbn;
        List<TitleIndexEntry> titleIndexEntries = new ArrayList<>();
    
        // Read all current title indexes
        try (RandomAccessFile titleIndexFile = new RandomAccessFile(TITLE_INDEX_FILE, "r")) {

            while (titleIndexFile.getFilePointer() < titleIndexFile.length()) {
                temp_currentTitle = titleIndexFile.readUTF();
                temp_currentIsbn = titleIndexFile.readUTF();
                titleIndexEntries.add(new TitleIndexEntry(temp_currentTitle, temp_currentIsbn));
            }
        }catch(FileNotFoundException e){
            System.out.println("File not found in addTitleIndex method, creating new file...");
        }
    
        // Add the new title index
        titleIndexEntries.add(new TitleIndexEntry(title, isbn));
    
        // Order the title indexes by title
        titleIndexEntries.sort(Comparator.comparing(TitleIndexEntry::getTitle));
    
        // Write the title indexes back to the file
        try (RandomAccessFile titleIndexFile = new RandomAccessFile(TITLE_INDEX_FILE, "rw")) {
            // Clear the file
            titleIndexFile.setLength(0);
            for (TitleIndexEntry entry : titleIndexEntries) {
                titleIndexFile.writeUTF(entry.getTitle());
                titleIndexFile.writeUTF(entry.getIsbn());
            }
        }
    }


    /**
     * Retrieves the ISBNs of mangas with the specified title.
     * The method reads the title index file and uses binary search to find the title index entries with the specified title.
     * @param title The title of the mangas to find.
     * @return A list of ISBNs of mangas with the specified title.
     * @throws IOException If an I/O error occurs.
     */
    public List<String> getIsbnsByTitle(String title) throws IOException {

        List<TitleIndexEntry> titleIndexEntries = new ArrayList<>();
        String temp_currentTitle;    
        String temp_isbn;

        // Read all current title indexes
        try (RandomAccessFile titleIndexFile = new RandomAccessFile(TITLE_INDEX_FILE, "r")) {
            while (titleIndexFile.getFilePointer() < titleIndexFile.length()) {
                temp_currentTitle = titleIndexFile.readUTF();
                temp_isbn = titleIndexFile.readUTF();
                titleIndexEntries.add(new TitleIndexEntry(temp_currentTitle, temp_isbn));
            }
        }
    
        // Use binary search to find the title 
        List<String> foundIsbns = new ArrayList<>();
        TitleIndexEntry midEntry;
        int mid;
        int cmp;
        int index;
        int left = 0;
        int right = titleIndexEntries.size() - 1;

        while (left <= right) {
            mid = (left + right) / 2;
            midEntry = titleIndexEntries.get(mid);
            cmp = midEntry.getTitle().compareTo(title);

            if (cmp < 0) {
                left = mid + 1;
            } else if (cmp > 0) {
                right = mid - 1;
            } else {
                // Found one title, now we need to find all titles with the same name
                // Search to the left first
                index = mid;
                while (index >= 0 && titleIndexEntries.get(index).getTitle().equals(title)) {
                    foundIsbns.add(titleIndexEntries.get(index).getIsbn());
                    index--;
                }
                // Now search to the right
                index = mid + 1;
                while (index < titleIndexEntries.size() && titleIndexEntries.get(index).getTitle().equals(title)) {
                    foundIsbns.add(titleIndexEntries.get(index).getIsbn());
                    index++;
                }
                break;
            }
        }
        return foundIsbns;
    }

    /**
     * Removes a title index entry from the title index file.
     * The method reads the current title index entries from the title index file and writes them back to a temporary file.
     * The method skips the title index entry with the specified ISBN and renames the temporary file to the title index file.
     * @param isbn_rem The ISBN of the manga to remove from the title index file.
     * @throws IOException If an I/O error occurs.
     */
    private void removeTitleIndex(String isbn_rem) throws IOException {

        File tempFile = new File(TEMP_FILE);
        String temp_currentTitle;    
        String temp_currentIsbn;


        try (RandomAccessFile titleIndexFile = new RandomAccessFile(TITLE_INDEX_FILE, "rw");
            RandomAccessFile tempTitleIndexFile = new RandomAccessFile(tempFile, "rw")) {
            while (titleIndexFile.getFilePointer() < titleIndexFile.length()) {
                temp_currentTitle = titleIndexFile.readUTF();
                temp_currentIsbn = titleIndexFile.readUTF();
                if (!temp_currentIsbn.equals(isbn_rem)) {
                    tempTitleIndexFile.writeUTF(temp_currentTitle);
                    tempTitleIndexFile.writeUTF(temp_currentIsbn);
                }
            }
        }
        tempFile.renameTo(new File(TITLE_INDEX_FILE));
    }

    /**
     * Retrieves all the titles of the mangas.
     * The method reads the title index file and retrieves all the titles.
     * @return A list of all the titles of the mangas.
     * @throws IOException If an I/O error occurs.
     */
    public List<String> getAllMangaTitles() throws IOException {
        List<String> titles = new ArrayList<>();
        String title;

        try (RandomAccessFile indexFile = new RandomAccessFile(TITLE_INDEX_FILE, "rw")) {

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




