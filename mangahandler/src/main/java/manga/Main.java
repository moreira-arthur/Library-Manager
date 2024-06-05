package manga;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MangaHandler manager = new MangaHandler();

        try {
            List<String> authors1 = Arrays.asList("Hajime Isayama");
            List<Integer> volumes1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34);
            Manga manga1 = new Manga("978-1234567890", "Attack on Titan", authors1, 2009, 2021, "Kodansha", "Action, Fantasy", "Bessatsu Shōnen Magazine", 2020, 34, 34, volumes1);
            manager.addManga(manga1);

            // Manga 2
            List<String> authors2 = Arrays.asList("Eiichiro Oda");
            List<Integer> volumes2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50);
            Manga manga2 = new Manga("978-2345678901", "One Piece", authors2, 1997, 2021, "Shueisha", "Adventure, Fantasy", "Weekly Shōnen Jump", 2021, 100, 100, volumes2);
            manager.addManga(manga2);

            // Manga 3
            List<String> authors3 = Arrays.asList("Masashi Kishimoto");
            List<Integer> volumes3 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72);
            Manga manga3 = new Manga("978-3456789012", "Naruto", authors3, 1999, 2014, "Weekly Shōnen Jump", "Adventure, Fantasy", "Shueisha", 2015, 72, 72, volumes3);
            manager.addManga(manga3);
    

            System.out.println("Mangas adicionados com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao adicionar mangas.");
        }
    }
}
