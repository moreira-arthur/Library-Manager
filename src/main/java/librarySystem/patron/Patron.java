package librarySystem.patron;

/**
 * Manga class
 * It represents a patron's object with its attributes
 * - name: Patron's first name
 * - lastName: Patron's last name
 * - cpf: Identification (Cadastro de Pessoa Física)
 * - password: Patron's password
 * - email: Patron's email
 * - phoneNumber: Patron's phone number
 */


public class Patron {
    private String name;
    private String lastName;
    private String cpf;
    private String password;

    private String email;
    private String phoneNumber;


    /**
     * Constructor
     * @param name Name
     * @param lastName Last Name
     * @param cpf CPF
     * @param email Email
     * @param phoneNumber Phone Number
     */
    public Patron(String name, String lastName, String cpf, String password,String email,String phoneNumber){
        this.name = name;
        this.lastName = lastName;
        this.cpf = cpf;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


    @Override
    public String toString() {
        return "Patron : " + name +" "+ lastName+ "\n"
                + "CPF : " + cpf + "\n"
                + "Email : " + email + "\n"
                + "Phone Number : " + phoneNumber + "\n";
    }

    /**
     * Gets patron's cpf
     * @return cpf
     */

    public String getCpf() {
        return cpf;
    }

    /**
     * Gets patron's password
     * @return password
     */
    public String getPassword(){
        return password;
    }

    /**
     * Gets patron's name
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Gets patron's lastName
     * @return lastName
     */
    public String getLastName(){
        return lastName;
    }

    /**
     * Gets patron's email
     * @return lastName
     */
    public String getEmail(){
        return email;
    }

    /**
     * Gets patron's phoneNumber
     * @return lastName
     */
    public String getPhoneNumber(){
        return phoneNumber;
    }
}
