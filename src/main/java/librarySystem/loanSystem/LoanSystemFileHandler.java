package librarySystem.loanSystem;

import librarySystem.book.BookHandler;
import librarySystem.patron.PatronHandler;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;

/**
 * Loan System File Handler Class: It represents
 * a loan system file handler object with its attributes
 * - filename: Name of the file
 * - isAvailable: Method to check if a book is available
 * - writeNewLoan: Method to write a new loan
 */
public class LoanSystemFileHandler {
    private static final String DATA_LOAN_FILE = "data_loan.dat";
    private static final String INDEX_LOAN_FILE = "index_loan.dat";
    private static final String TEMP_FILE = "temp.dat";
    private BookHandler bookHandler = new BookHandler();
    private PatronHandler patronHandler = new PatronHandler();


    public boolean isBookAvailable(String isbn) throws IOException {
        if(bookHandler.getBook(isbn) == null){
            throw new IOException("Book not found");
        }

        try (RandomAccessFile index_file = new RandomAccessFile(INDEX_LOAN_FILE, "rw")) {
            while (index_file.getFilePointer() < index_file.length()) {
                String tempIsbn = index_file.readUTF();
                String status = index_file.readUTF();
                index_file.readLong();
                if (tempIsbn.equals(isbn) && status.equals("OPEN  ")){
                    return false;
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found, creating file...");
            return true;
        }
    }

    public boolean isCpfRegistered(String cpf) throws IOException{
        if(patronHandler.searchPatronByCpf(cpf) == null){
            throw new IOException("Patron not found");
        }
        return true;
    }

    public void newLoan(LoanSystem loan) throws IOException {
        Boolean writtenIndex = false;

        try(RandomAccessFile file = new RandomAccessFile(DATA_LOAN_FILE, "rw")) {
            file.seek(file.length());
            try(RandomAccessFile indexFile = new RandomAccessFile(INDEX_LOAN_FILE, "rw")) {
                while(indexFile.getFilePointer() < indexFile.length()){
                    String tempIsbn = indexFile.readUTF();
                    if(tempIsbn.equals(loan.getLoanedIsbn())){
                        removeIndex(tempIsbn);
                        writeIndex(tempIsbn, file.getFilePointer());
                        writtenIndex = true;
                        break;
                    }
                    indexFile.readUTF();
                    indexFile.readLong();
                }
            }
            if(!writtenIndex){
                writeIndex(loan.getLoanedIsbn(), file.getFilePointer());
            }
            file.writeUTF(loan.getLoanedIsbn());
            file.writeUTF(loan.getcpf());
            file.writeUTF(loan.getStartDate().toString());
            file.writeUTF(loan.getDueDate().toString());
            file.writeUTF(loan.getStatus());
        } catch (FileNotFoundException e) {
            System.out.println("File not found, creating file...");
        }
    }

    public void writeIndex(String isbn, long offset) throws IOException {
        try(RandomAccessFile file = new RandomAccessFile(INDEX_LOAN_FILE, "rw")) {
            file.seek(file.length());
            file.writeUTF(isbn);
            file.writeUTF("OPEN  ");
            file.writeLong(offset);
        } catch (FileNotFoundException e) {
            System.out.println("File not found, creating file...");
        }
    }

    public void closeLoan(String isbn) throws IOException {
        try(RandomAccessFile indexFile = new RandomAccessFile(INDEX_LOAN_FILE, "rw")) {
            while(indexFile.getFilePointer() < indexFile.length()){
                String tempIsbn = indexFile.readUTF();
                String status = indexFile.readUTF();
                long offset = indexFile.readLong();
                System.out.println("ISBN: " + tempIsbn + "\nStatus: " + status + "\nOffset: " + offset);
                if(tempIsbn.equals(isbn) && !status.equals("CLOSED")){
                    try(RandomAccessFile dataFile = new RandomAccessFile(DATA_LOAN_FILE, "rw")) {
                        dataFile.seek(offset);
                        dataFile.readUTF();
                        dataFile.readUTF();
                        dataFile.readUTF();
                        dataFile.readUTF();
                        dataFile.writeUTF("CLOSED");
                        changeStatusIndexFile(isbn,"CLOSED");
                        return;
                    }
                }
            }
        }catch (EOFException eofException) {
            System.out.println("Loan not found");
        }
    }

    public String checkLoan(String isbn) throws IOException{
        try(RandomAccessFile indexFile = new RandomAccessFile(INDEX_LOAN_FILE,"rw")){
            while(indexFile.getFilePointer() < indexFile.length()){
                String tempisbn = indexFile.readUTF();
                String status = indexFile.readUTF();
                long offset = indexFile.readLong();
                if(!status.equals("CLOSED") && tempisbn.equals(isbn)){
                    try(RandomAccessFile dataFile = new RandomAccessFile(DATA_LOAN_FILE,"rw")){
                        dataFile.seek(offset);
                        String loanedIsbn = dataFile.readUTF();
                        String cpf = dataFile.readUTF();
                        LocalDate startDate = LocalDate.parse(dataFile.readUTF());
                        LocalDate dueDate = LocalDate.parse(dataFile.readUTF());
                        String statusLoan;
                        if (dueDate.isBefore(LocalDate.now())) {
                            statusLoan = "LATE  ";
                            dataFile.writeUTF(statusLoan);
                            changeStatusIndexFile(isbn,statusLoan);
                        }else{
                            statusLoan = dataFile.readUTF();
                        }
                        return "ISBN: " + loanedIsbn +
                                "\nCPF: " + cpf+
                                "\nStart Date: " + startDate+
                                "\nDue Date: " + dueDate +
                                "\nStatus: " + statusLoan;
                    }
                }
            }
            throw new IOException("Loan not found");
        }
    }


    public void changeStatusIndexFile(String isbn,String status) throws IOException   {
        try(RandomAccessFile indexFIle = new RandomAccessFile(INDEX_LOAN_FILE,"rw")){
            while(indexFIle.getFilePointer() < indexFIle.length()){
                String tempIsbn = indexFIle.readUTF();
                if(tempIsbn.equals(isbn)){
                    indexFIle.writeUTF(status);
                    return;
                }
                indexFIle.readUTF();
                indexFIle.readLong();
            }
        }catch (Exception e){
            System.out.println("Error changing status of loan");
        }
    }

    public boolean isLate(String isbn) throws IOException{
        try(RandomAccessFile indexFile = new RandomAccessFile(INDEX_LOAN_FILE,"rw")){
            while(indexFile.getFilePointer() < indexFile.length()){
                String tempisbn = indexFile.readUTF();
                String status = indexFile.readUTF();
                indexFile.readLong();
                if(status.equals("LATE  ") && tempisbn.equals(isbn)){
                    return true;
                }
            }
            return false;
        }catch (EOFException e){
            return false;
        }
    }

    public void removeIndex(String isbn_rem) throws IOException {

        File tempFile = new File(TEMP_FILE);
        String temp_currentIsbn;
        String temp_currentFlag;
        long temp_currentOffset;

        try (RandomAccessFile secondaryIndexFile = new RandomAccessFile(INDEX_LOAN_FILE, "rw");
            RandomAccessFile tempsecondaryIndexFile = new RandomAccessFile(tempFile, "rw")) {
            while (secondaryIndexFile.getFilePointer() < secondaryIndexFile.length()) {
                temp_currentIsbn = secondaryIndexFile.readUTF();
                temp_currentFlag = secondaryIndexFile.readUTF();
                temp_currentOffset = secondaryIndexFile.readLong();
                if (!temp_currentIsbn.equals(isbn_rem)) {
                    tempsecondaryIndexFile.writeUTF(temp_currentIsbn);
                    tempsecondaryIndexFile.writeUTF(temp_currentFlag);
                    tempsecondaryIndexFile.writeLong(temp_currentOffset);
                }
            }
        }
        tempFile.renameTo(new File(INDEX_LOAN_FILE));
    }
}


