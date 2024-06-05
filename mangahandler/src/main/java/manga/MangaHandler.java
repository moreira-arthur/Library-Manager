package manga;

import java.io.*;
import java.util.*;

public class MangaHandler {
    private static final String DATA_FILE = "manga.dat";
    private static final String INDEX_FILE = "index.dat";
    private static final String TITLE_INDEX_FILE = "title_index.dat";
    private static final int RECORD_SIZE = 2048;


    public void addManga(Manga manga) throws IOException {
        try (RandomAccessFile dataFile = new RandomAccessFile(DATA_FILE, "rw")) {
            long filePointer = dataFile.length();
            dataFile.seek(filePointer);
            writeManga(dataFile, manga);
            addIndex(manga.getIsbn(), filePointer);
            addTitleIndex(manga.getTitle(), manga.getIsbn());
        }
    }

    public Manga getManga(String isbn) throws IOException {
        long filePointer = getIndex(isbn);
        if (filePointer == -1) return null;
        try (RandomAccessFile dataFile = new RandomAccessFile(DATA_FILE, "r")) {
            dataFile.seek(filePointer);
            return readManga(dataFile);
        }
    }

    public void updateManga(String isbn, Manga updatedManga) throws IOException {
        long filePointer = getIndex(isbn);
        if (filePointer == -1) return;
        try (RandomAccessFile dataFile = new RandomAccessFile(DATA_FILE, "rw")) {
            dataFile.seek(filePointer);
            writeManga(dataFile, updatedManga);
        }
    }

    public void deleteManga(String isbn) throws IOException {
        long filePointer = getIndex(isbn);
        if (filePointer == -1){
            throw new IOException("Manga não encontrado");
        }
            
        try (RandomAccessFile dataFile = new RandomAccessFile(DATA_FILE, "rw")) {
            dataFile.seek(filePointer);
            // Marca o registro como deletado
            dataFile.writeChar('*');
        }
        removeIndex(isbn);
        removeTitleIndex(isbn);
    }



    public List<Manga> searchMangasByTitle(String title) throws IOException {
        List<String> isbns = getIsbnsByTitle(title);
        List<Manga> mangas = new ArrayList<>();
        for (String isbn : isbns) {
            Manga manga = getManga(isbn);
            if (manga != null) {
                mangas.add(manga);
            }
        }
        return mangas;
    }

    public List<Manga> searchMangaByIsbn(String isbn) throws IOException {
        List<Manga> mangas = new ArrayList<>();
        Manga manga = getManga(isbn);
        if (manga != null) {
            mangas.add(manga);
        }
        return mangas;
    }

    private void writeManga(RandomAccessFile file, Manga manga) throws IOException {
        long startPointer = file.getFilePointer();
        file.writeUTF(manga.getIsbn());
        file.writeUTF(manga.getTitle());
        file.writeInt(manga.getAuthors().size());
        for (String author : manga.getAuthors()) {
            file.writeUTF(author);
        }
        file.writeInt(manga.getStartYear());
        file.writeInt(manga.getEndYear());
        file.writeUTF(manga.getGenre());
        file.writeUTF(manga.getMagazine());
        file.writeUTF(manga.getPublisher());
        file.writeInt(manga.getEditionYear());
        file.writeInt(manga.getTotalVolumes());
        file.writeInt(manga.getAcquiredVolumesCount());
        for (Integer volume : manga.getAcquiredVolumes()) {
            file.writeInt(volume);
        }
        long endPointer = file.getFilePointer();
        long remainingBytes = RECORD_SIZE - (endPointer - startPointer);
        if (remainingBytes > 0) {
            file.write(new byte[(int) remainingBytes]);
        }
    }

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

    private void addIndex(String isbn, long filePointer) throws IOException {
        List<IndexEntry> indexEntries = new ArrayList<>();
    
        // Lê todos os índices atuais
        try (RandomAccessFile indexFile = new RandomAccessFile(INDEX_FILE, "r")) {
            while (indexFile.getFilePointer() < indexFile.length()) {
                String currentIsbn = indexFile.readUTF();
                long currentPointer = indexFile.readLong();
                indexEntries.add(new IndexEntry(currentIsbn, currentPointer));
            }
        }catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado, criando novo arquivo...");       
        }catch(IOException e){
            System.out.println("Erro de I/O");
        }catch(Exception e){
            System.out.println("Erro desconhecido");
        }
    
        // Adiciona o novo índice
        indexEntries.add(new IndexEntry(isbn, filePointer));
    
        // Ordena os índices
        Collections.sort(indexEntries, Comparator.comparing(IndexEntry::getIsbn));
    
        // Escreve os índices ordenados de volta ao arquivo
        try (RandomAccessFile indexFile = new RandomAccessFile(INDEX_FILE, "rw")) {
            indexFile.setLength(0); // Limpa o arquivo
            for (IndexEntry entry : indexEntries) {
                indexFile.writeUTF(entry.getIsbn());
                indexFile.writeLong(entry.getFilePointer());
            }
        }
    }

    private long getIndex(String isbn) throws IOException {
        List<IndexEntry> indexEntries = new ArrayList<>();
    
        // Lê todos os índices
        try (RandomAccessFile indexFile = new RandomAccessFile(INDEX_FILE, "r")) {
            while (indexFile.getFilePointer() < indexFile.length()) {
                String currentIsbn = indexFile.readUTF();
                long filePointer = indexFile.readLong();
                indexEntries.add(new IndexEntry(currentIsbn, filePointer));
            }
        }
    
        // Usa busca binária para encontrar o índice
        int left = 0;
        int right = indexEntries.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            IndexEntry midEntry = indexEntries.get(mid);
            int cmp = midEntry.getIsbn().compareTo(isbn);
            if (cmp < 0) {
                left = mid + 1;
            } else if (cmp > 0) {
                right = mid - 1;
            } else {
                return midEntry.getFilePointer();
            }
        }
        return -1; // Não encontrado
    }

    private void removeIndex(String isbn) throws IOException {
        File tempFile = new File("temp_index.dat");
        try (RandomAccessFile indexFile = new RandomAccessFile(INDEX_FILE, "rw");
            RandomAccessFile tempIndexFile = new RandomAccessFile(tempFile, "rw")) {
            while (indexFile.getFilePointer() < indexFile.length()) {
                String currentIsbn = indexFile.readUTF();
                long filePointer = indexFile.readLong();
                if (!currentIsbn.equals(isbn)) {
                    tempIndexFile.writeUTF(currentIsbn);
                    tempIndexFile.writeLong(filePointer);
                }
            }
        }
        tempFile.renameTo(new File(INDEX_FILE));
    }

    private void addTitleIndex(String title, String isbn) throws IOException {
        List<TitleIndexEntry> titleIndexEntries = new ArrayList<>();
    
        // Lê todos os índices de título atuais
        try (RandomAccessFile titleIndexFile = new RandomAccessFile(TITLE_INDEX_FILE, "r")) {
            while (titleIndexFile.getFilePointer() < titleIndexFile.length()) {
                String currentTitle = titleIndexFile.readUTF();
                String currentIsbn = titleIndexFile.readUTF();
                titleIndexEntries.add(new TitleIndexEntry(currentTitle, currentIsbn));
            }
        }catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado, criando novo arquivo...");
        }
    
        // Adiciona o novo índice de título
        titleIndexEntries.add(new TitleIndexEntry(title, isbn));
    
        // Ordena os índices de título
        Collections.sort(titleIndexEntries, Comparator.comparing(TitleIndexEntry::getTitle));
    
        // Escreve os índices de título ordenados de volta ao arquivo
        try (RandomAccessFile titleIndexFile = new RandomAccessFile(TITLE_INDEX_FILE, "rw")) {
            titleIndexFile.setLength(0); // Limpa o arquivo
            for (TitleIndexEntry entry : titleIndexEntries) {
                titleIndexFile.writeUTF(entry.getTitle());
                titleIndexFile.writeUTF(entry.getIsbn());
            }
        }
    }

    private List<String> getIsbnsByTitle(String title) throws IOException {
        List<TitleIndexEntry> titleIndexEntries = new ArrayList<>();
    
        // Lê todos os índices de título
        try (RandomAccessFile titleIndexFile = new RandomAccessFile(TITLE_INDEX_FILE, "r")) {
            while (titleIndexFile.getFilePointer() < titleIndexFile.length()) {
                String currentTitle = titleIndexFile.readUTF();
                String isbn = titleIndexFile.readUTF();
                titleIndexEntries.add(new TitleIndexEntry(currentTitle, isbn));
            }
        }
    
        // Usa busca binária para encontrar o índice de título
        List<String> isbns = new ArrayList<>();
        int left = 0;
        int right = titleIndexEntries.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            TitleIndexEntry midEntry = titleIndexEntries.get(mid);
            int cmp = midEntry.getTitle().compareTo(title);
            if (cmp < 0) {
                left = mid + 1;
            } else if (cmp > 0) {
                right = mid - 1;
            } else {
                // Encontramos um título correspondente, agora procuramos em ambas as direções
                int index = mid;
                while (index >= 0 && titleIndexEntries.get(index).getTitle().equals(title)) {
                    isbns.add(titleIndexEntries.get(index).getIsbn());
                    index--;
                }
                index = mid + 1;
                while (index < titleIndexEntries.size() && titleIndexEntries.get(index).getTitle().equals(title)) {
                    isbns.add(titleIndexEntries.get(index).getIsbn());
                    index++;
                }
                break;
            }
        }
        return isbns;
    }

    private void removeTitleIndex(String isbn) throws IOException {
        File tempFile = new File("temp_title_index.dat");
        try (RandomAccessFile titleIndexFile = new RandomAccessFile(TITLE_INDEX_FILE, "rw");
            RandomAccessFile tempTitleIndexFile = new RandomAccessFile(tempFile, "rw")) {
            while (titleIndexFile.getFilePointer() < titleIndexFile.length()) {
                String title = titleIndexFile.readUTF();
                String currentIsbn = titleIndexFile.readUTF();
                if (!currentIsbn.equals(isbn)) {
                    tempTitleIndexFile.writeUTF(title);
                    tempTitleIndexFile.writeUTF(currentIsbn);
                }
            }
        }
        tempFile.renameTo(new File(TITLE_INDEX_FILE));
    }

    public String getAllMangasAsString(){
        try (RandomAccessFile dataFile = new RandomAccessFile(DATA_FILE, "r")) {
            StringBuilder mangas = new StringBuilder();
            while (dataFile.getFilePointer() < dataFile.length()) {
                mangas.append(readManga(dataFile).toString());
                mangas.append("\n");
            }
            return mangas.toString();
        }
        catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado");
            return "";
        }
        catch(IOException e){
            System.out.println("Erro de I/O");
            return "";
        }
        catch(Exception e){
            System.out.println("Erro desconhecido");
            return "";
        }
    }
}

class IndexEntry {
    private String isbn;
    private long filePointer;

    public IndexEntry(String isbn, long filePointer) {
        this.isbn = isbn;
        this.filePointer = filePointer;
    }

    public String getIsbn() {
        return isbn;
    }

    public long getFilePointer() {
        return filePointer;
    }
}

class TitleIndexEntry {
    private String title;
    private String isbn;

    public TitleIndexEntry(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }
}
