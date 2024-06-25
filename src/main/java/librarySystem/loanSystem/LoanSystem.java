package librarySystem.loanSystem;


import java.time.LocalDate;

/**
 * Loan System Class: It represents
 * a loan system object with its attributes
 * - loanedIsbn: Book that is being loaned
 * - cpf: cpf that is loaning the book
 * - startDate: Date when the loan started
 * - dueDate: Date when the book is due
 * - status: Status of the loan
 */

public class LoanSystem {
    private String loanedIsbn;
    private String cpf;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String status;

    public LoanSystem(String loanedIsbn,String cpf,LocalDate startDate,LocalDate dueDate,String status){
        this.loanedIsbn = loanedIsbn;
        this.cpf = cpf;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    /**
     * Gets the loaned book
     * @return loanedIsbn
     */
    public String getLoanedIsbn(){
        return loanedIsbn;
    }

    /**
     * Gets the cpf
     * @return cpf
     */
    public String getcpf(){
        return cpf;
    }

    /**
     * Gets the start date
     * @return startDate
     */
    public LocalDate getStartDate(){
        return startDate;
    }

    /**
     * Gets the due date
     * @return dueDate
     */
    public LocalDate getDueDate(){
        return dueDate;
    }

    /**
     * Gets the status
     * @return status
     */
    public String getStatus(){
        return status;
    }


}
