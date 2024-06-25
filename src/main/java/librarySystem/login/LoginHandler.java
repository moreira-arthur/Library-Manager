package librarySystem.login;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;


/** 
 * Class to handle logins
 * It has methods to check if a login is valid, add a login and remove a login
*/
public class LoginHandler {
    private static final String LOGINS_FILE = "logins.dat";
    private static final String TEMP_FILE = "temp.dat";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "adminpsw";


    /**
     * Method to check if a login is valid
     * @param login The login to check
     * @return True if the login is valid, false otherwise
     */
    public boolean isValidLogin(Login login) {
        return getLogin(login.getUsername()) != null;
    }
    
    /**
     * Method to check if a login is the admin login
     * @param login The login to check
     * @return True if the login is the admin login, false otherwise
     */
    public boolean isAdminLogin(Login login) {
        return login.getUsername().equals(ADMIN_USERNAME) && login.getPassword().equals(ADMIN_PASSWORD);
    }

    /**
     * Method to add a login
     * @param login The login to add
     * @throws IOException If the login already exists
     */
    public void addLogin(Login login) throws IOException {
        long filePointer;

        if(getLogin(login.getUsername()) != null){
            throw new IOException("Login already exists");
        }

        try (RandomAccessFile dataFile = new RandomAccessFile(LOGINS_FILE, "rw")) {
                filePointer = dataFile.length();
        }catch(FileNotFoundException e){
            System.out.println("File not found, creating new file...");
            filePointer = 0;
        }
        try (RandomAccessFile dataFile = new RandomAccessFile(LOGINS_FILE, "rw")) {
            dataFile.seek(filePointer);
            writeLoginToFile(dataFile, login);
        }catch(FileNotFoundException e){
            System.out.println("FIle not found, creating new file...");
        }

    }

    /**
     * Method to get a login
     * @param username The username of the login
     * @return The login if it exists, null otherwise
     */
    private Login getLogin(String username){
        List<Login> logins = new ArrayList<>();
        String currentUsername;
        String currentPassword;

        // Read all current indexes
        try (RandomAccessFile loginFile = new RandomAccessFile(LOGINS_FILE, "r")) {
            while (loginFile.getFilePointer() < loginFile.length()) {
                currentUsername = loginFile.readUTF();
                currentPassword = loginFile.readUTF();
                logins.add(new Login(currentUsername, currentPassword));
            }
        }catch(FileNotFoundException e){
            System.out.println("File not found in getLogin method");
        }catch (IOException e) {
            System.out.println("Error reading file in getLogin method");
        }

        // Use binary search to find the index
        Login midLogin;
        int mid;
        int cmp;
        int left = 0;
        int right = logins.size() - 1;

        while (left <= right) {
            mid = (left + right) / 2;
            midLogin = logins.get(mid);
            cmp = midLogin.getUsername().compareTo(username);
            if (cmp < 0) {
                left = mid + 1;
            } else if (cmp > 0) {
                right = mid - 1;
            } else {
                return midLogin;
            }
        }
        return null;
    }

    /**
     * Method to remove a login
     * @param login The login to remove
     * @throws IOException If the login does not exist
     */
    public void removeLogin(Login login) throws IOException {
        if(getLogin(login.getUsername()) == null){
            throw new IOException("Login does not exist");
        }
        File tempFile = new File(TEMP_FILE);
        try (RandomAccessFile dataFile = new RandomAccessFile(LOGINS_FILE, "rw");
        RandomAccessFile tempDataFile = new RandomAccessFile(tempFile, "rw")) {
            while (dataFile.getFilePointer() < dataFile.length()) {
                String username = dataFile.readUTF();
                String password = dataFile.readUTF();
                if (!username.equals(login.getUsername()) && !password.equals(login.getPassword())) {
                    writeLoginToFile(tempDataFile, login);
                }
            }
        }
        tempFile.renameTo(new File(LOGINS_FILE));
    }
    
    /**
     * Method to write a login to a file
     * @param dataFile The file to write to
     * @param login The login to write
     * @throws IOException If an I/O error occurs
     */
    private void writeLoginToFile(RandomAccessFile dataFile, Login login) throws IOException {
        dataFile.writeUTF(login.getUsername());
        dataFile.writeUTF(login.getPassword());
    }
    
}