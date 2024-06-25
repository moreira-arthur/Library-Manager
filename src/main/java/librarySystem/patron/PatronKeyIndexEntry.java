package librarySystem.patron;

/**
 * LastNameIndexEntry class
 * It represents a key index entry object with its attributes
 * - key: Last Name
 * - cpf: Identification (Cadastro de Pessoa Fisica)
 */
public class PatronKeyIndexEntry {
    private final String key;
    private final String cpf;

    /**
     * Constructor
     * @param key Last Name
     * @param cpf CPF
     */
    public PatronKeyIndexEntry(String key, String cpf) {
        this.key = key;
        this.cpf = cpf;
    }

    /**
     * Get the key
     * @return Last Name
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the cpf
     * @return cpf
     */
    public String getCpf() {
        return cpf;
    }
}
