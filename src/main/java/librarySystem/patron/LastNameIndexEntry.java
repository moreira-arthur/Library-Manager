package librarySystem.patron;

/**
 * LastNameIndexEntry class
 * It represents a lastName index entry object with its attributes
 * - lastName: Last Name
 * - cpf: Identification (Cadastro de Pessoa Fisica)
 */
public class LastNameIndexEntry {
    private final String lastName;
    private final String cpf;

    /**
     * Constructor
     * @param lastName Last Name
     * @param cpf CPF
     */
    public LastNameIndexEntry(String lastName, String cpf) {
        this.lastName = lastName;
        this.cpf = cpf;
    }

    /**
     * Get the lastName
     * @return Last Name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get the cpf
     * @return cpf
     */
    public String getCpf() {
        return cpf;
    }
}
