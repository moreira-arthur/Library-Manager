package librarySystem.patron;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PatronSecondaryIndexManager {

    private static final String TEMP_FILE = "temp.dat";
    private String filename;

    public PatronSecondaryIndexManager(String filename){
        this.filename = filename;
    }

    /**
     * Adds a key index entry to the key index file.
     * The method reads the current key index entries from the key index file and adds the new key index entry.
     * The method sorts the key index entries by key and writes them back to the key index file.
     * @param key The key of the patron to add to the key index file.
     * @param cpf The ISBN of the patron to add to the key index file.
     * @throws IOException If an I/O error occurs.
     */

    public void addSecondaryIndex(String key, String cpf) throws IOException {
        String temp_currentLastName;
        String temp_currentCpf;
        List<PatronKeyIndexEntry> patronKeyIndexEntry = new ArrayList<>();

        // Read all current last name indexes
        try (RandomAccessFile secondaryIndexFile = new RandomAccessFile(filename, "r")) {

            while (secondaryIndexFile.getFilePointer() < secondaryIndexFile.length()) {
                temp_currentLastName = secondaryIndexFile.readUTF();
                temp_currentCpf = secondaryIndexFile.readUTF();
                patronKeyIndexEntry.add(new PatronKeyIndexEntry(temp_currentLastName, temp_currentCpf));
            }
        }catch(FileNotFoundException e){
            System.out.println("File not found in addLastNameIndex method, creating new file...");
        }

        // Add the new last name index
        patronKeyIndexEntry.add(new PatronKeyIndexEntry(key, cpf));

        // Order the last name indexes by last name
        patronKeyIndexEntry.sort(Comparator.comparing(PatronKeyIndexEntry::getKey));

        // Write the last name indexes back to the file
        try (RandomAccessFile secondaryIndexFile = new RandomAccessFile(filename, "rw")) {
            // Clear the file
            secondaryIndexFile.setLength(0);
            for (PatronKeyIndexEntry entry : patronKeyIndexEntry) {
                secondaryIndexFile.writeUTF(entry.getKey());
                secondaryIndexFile.writeUTF(entry.getCpf());
            }
        }
    }


    /**
     * Retrieves the ISBNs of mangas with the specified last name.
     * The method reads the last name index file and uses binary search to find the last name index entries with the specified last name.
     * @param last name The last name of the mangas to find.
     * @return A list of ISBNs of mangas with the specified last name.
     * @throws IOException If an I/O error occurs.
     */
    public List<String> getCpfsByKey(String key) throws IOException {

        List<PatronKeyIndexEntry> patronKeyIndexEntry = new ArrayList<>();
        String temp_currentLastName;
        String temp_cpf;

        // Read all current last name indexes
        try (RandomAccessFile secondaryIndexFile = new RandomAccessFile(filename, "r")) {
            while (secondaryIndexFile.getFilePointer() < secondaryIndexFile.length()) {
                temp_currentLastName = secondaryIndexFile.readUTF();
                temp_cpf = secondaryIndexFile.readUTF();
                patronKeyIndexEntry.add(new PatronKeyIndexEntry(temp_currentLastName, temp_cpf));
            }
        }

        // Use binary search to find the last name
        List<String> foundCpfs = new ArrayList<>();
        PatronKeyIndexEntry midEntry;
        int mid;
        int cmp;
        int index;
        int left = 0;
        int right = patronKeyIndexEntry.size() - 1;

        while (left <= right) {
            mid = (left + right) / 2;
            midEntry = patronKeyIndexEntry.get(mid);
            cmp = midEntry.getKey().compareTo(key);

            if (cmp < 0) {
                left = mid + 1;
            } else if (cmp > 0) {
                right = mid - 1;
            } else {
                // Found one last name, now we need to find all last names with the same name
                // Search to the left first
                index = mid;
                while (index >= 0 && patronKeyIndexEntry.get(index).getKey().equals(key)) {
                    foundCpfs.add(patronKeyIndexEntry.get(index).getCpf());
                    index--;
                }
                // Now search to the right
                index = mid + 1;
                while (index < patronKeyIndexEntry.size() && patronKeyIndexEntry.get(index).getKey().equals(key)) {
                    foundCpfs.add(patronKeyIndexEntry.get(index).getCpf());
                    index++;
                }
                break;
            }
        }
        return foundCpfs;
    }

    /**
     * Removes a last name index entry from the last name index file.
     * The method reads the current last name index entries from the last name index file and writes them back to a temporary file.
     * The method skips the last name index entry with the specified ISBN and renames the temporary file to the last name index file.
     * @param cpf_rem The ISBN of the patron to remove from the last name index file.
     * @throws IOException If an I/O error occurs.
     */
    public void removeSecondaryIndex(String cpf_rem) throws IOException {

        File tempFile = new File(TEMP_FILE);
        String temp_currentLastName;
        String temp_currentCpf;


        try (RandomAccessFile secondaryIndexFile = new RandomAccessFile(filename, "rw");
             RandomAccessFile tempsecondaryIndexFile = new RandomAccessFile(tempFile, "rw")) {
            while (secondaryIndexFile.getFilePointer() < secondaryIndexFile.length()) {
                temp_currentLastName = secondaryIndexFile.readUTF();
                temp_currentCpf = secondaryIndexFile.readUTF();
                if (!temp_currentCpf.equals(cpf_rem)) {
                    tempsecondaryIndexFile.writeUTF(temp_currentLastName);
                    tempsecondaryIndexFile.writeUTF(temp_currentCpf);
                }
            }
        }
        tempFile.renameTo(new File(filename));
    }


}
