package librarySystem.book;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SecondayIndexManager {

    private static final String TEMP_FILE = "temp.dat";
    private String filename;

    public SecondayIndexManager(String filename){
        this.filename = filename;
    }

    /**
     * Adds a key index entry to the key index file.
     * The method reads the current key index entries from the key index file and adds the new key index entry.
     * The method sorts the key index entries by key and writes them back to the key index file.
     * @param key The key of the book to add to the key index file.
     * @param isbn The ISBN of the book to add to the key index file.
     * @throws IOException If an I/O error occurs.
     */

    public void addSecondaryIndex(String key, String isbn) throws IOException {
        String temp_currentTitle;
        String temp_currentIsbn;
        List<KeyIndexEntry> keyIndexEntry = new ArrayList<>();

        // Read all current title indexes
        try (RandomAccessFile secondaryIndexFile = new RandomAccessFile(filename, "r")) {

            while (secondaryIndexFile.getFilePointer() < secondaryIndexFile.length()) {
                temp_currentTitle = secondaryIndexFile.readUTF();
                temp_currentIsbn = secondaryIndexFile.readUTF();
                keyIndexEntry.add(new KeyIndexEntry(temp_currentTitle, temp_currentIsbn));
            }
        }catch(FileNotFoundException e){
            System.out.println("File not found in addTitleIndex method, creating new file...");
        }

        // Add the new title index
        keyIndexEntry.add(new KeyIndexEntry(key, isbn));

        // Order the title indexes by title
        keyIndexEntry.sort(Comparator.comparing(KeyIndexEntry::getKey));

        // Write the title indexes back to the file
        try (RandomAccessFile secondaryIndexFile = new RandomAccessFile(filename, "rw")) {
            // Clear the file
            secondaryIndexFile.setLength(0);
            for (KeyIndexEntry entry : keyIndexEntry) {
                secondaryIndexFile.writeUTF(entry.getKey());
                secondaryIndexFile.writeUTF(entry.getIsbn());
            }
        }
    }
    

        /**
     * Retrieves the ISBNs of books with the specified title.
     * The method reads the title index file and uses binary search to find the title index entries with the specified title.
     * @param title The title of the books to find.
     * @return A list of ISBNs of books with the specified title.
     * @throws IOException If an I/O error occurs.
     */
    public List<String> getIsbnsByKey(String key) throws IOException {

        List<KeyIndexEntry> keyIndexEntry = new ArrayList<>();
        String temp_currentTitle;
        String temp_isbn;

        // Read all current title indexes
        try (RandomAccessFile secondaryIndexFile = new RandomAccessFile(filename, "r")) {
            while (secondaryIndexFile.getFilePointer() < secondaryIndexFile.length()) {
                temp_currentTitle = secondaryIndexFile.readUTF();
                temp_isbn = secondaryIndexFile.readUTF();
                keyIndexEntry.add(new KeyIndexEntry(temp_currentTitle, temp_isbn));
            }
        }

        // Use binary search to find the title
        List<String> foundIsbns = new ArrayList<>();
        KeyIndexEntry midEntry;
        int mid;
        int cmp;
        int index;
        int left = 0;
        int right = keyIndexEntry.size() - 1;

        while (left <= right) {
            mid = (left + right) / 2;
            midEntry = keyIndexEntry.get(mid);
            cmp = midEntry.getKey().compareTo(key);

            if (cmp < 0) {
                left = mid + 1;
            } else if (cmp > 0) {
                right = mid - 1;
            } else {
                // Found one title, now we need to find all titles with the same name
                // Search to the left first
                index = mid;
                while (index >= 0 && keyIndexEntry.get(index).getKey().equals(key)) {
                    foundIsbns.add(keyIndexEntry.get(index).getIsbn());
                    index--;
                }
                // Now search to the right
                index = mid + 1;
                while (index < keyIndexEntry.size() && keyIndexEntry.get(index).getKey().equals(key)) {
                    foundIsbns.add(keyIndexEntry.get(index).getIsbn());
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
     * @param isbn_rem The ISBN of the book to remove from the title index file.
     * @throws IOException If an I/O error occurs.
     */
    public void removeSecondaryIndex(String isbn_rem) throws IOException {

        File tempFile = new File(TEMP_FILE);
        String temp_currentTitle;    
        String temp_currentIsbn;


        try (RandomAccessFile secondaryIndexFile = new RandomAccessFile(filename, "rw");
            RandomAccessFile tempsecondaryIndexFile = new RandomAccessFile(tempFile, "rw")) {
            while (secondaryIndexFile.getFilePointer() < secondaryIndexFile.length()) {
                temp_currentTitle = secondaryIndexFile.readUTF();
                temp_currentIsbn = secondaryIndexFile.readUTF();
                if (!temp_currentIsbn.equals(isbn_rem)) {
                    tempsecondaryIndexFile.writeUTF(temp_currentTitle);
                    tempsecondaryIndexFile.writeUTF(temp_currentIsbn);
                }
            }
        }
        tempFile.renameTo(new File(filename));
    }

        
}