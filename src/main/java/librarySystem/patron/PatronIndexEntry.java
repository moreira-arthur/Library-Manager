package librarySystem.patron;

/**
 * IndexEntry class
 * It represents an index entry object with its attributes
 * - cpf: International Standard Book Number
 * - filePointer: File pointer
 */
public class PatronIndexEntry {
    private final  String cpf;
    private final  long filePointer;

    /**
     * Constructor
     * @param cpf ISBN
     * @param filePointer File pointer
     */
    public PatronIndexEntry(String cpf, long filePointer) {
        this.cpf = cpf;
        this.filePointer = filePointer;
    }

    /**
     * Get the CPF
     * @return CPF
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Get the file pointer
     * @return File pointer
     */
    public long getFilePointer() {
        return filePointer;
    }
}
