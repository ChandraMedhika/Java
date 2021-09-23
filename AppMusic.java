import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AppMusic{

    static String fileName;
    static ArrayList<String> appLists;
    static boolean isEditing = false;
    static Scanner input;
    //String jwb;

    public static void main(String[] args) {
        String jwb;
        appLists = new ArrayList<>();
        input = new Scanner(System.in);

        System.out.println("Apakah ingin menggunakan aplikasi Music Player(ya/tidak)?");
        jwb = input.nextLine();

        if(jwb.equals("ya")){

        String filePath = System.console() == null ? "/src/applist.txt" : "/applist.txt";
        fileName = System.getProperty("user.dir") + filePath;

        System.out.println("FILE: " + fileName);

        while (true) {
            showMenu();
        }
      }else if(jwb.equals("ya")){
        System.out.println("keluar dari aplikasi");
      }
    }

    static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                // clear screen untuk windows
                new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO()
                        .start()
                        .waitFor();
            } else {
                // clear screen untuk Linux, Unix, Mac
                Runtime.getRuntime().exec("clear");
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            System.out.println("Error karena: " + e.getMessage());
        }

    }

    static void showMenu() {
        System.out.println("=== MUSIC PLAYER APP ===");
        System.out.println("[1] Lihat Daftar Musik");
        System.out.println("[2] Tambah Judul Lagu");
        System.out.println("[3] Hapus Judul Lagu");
        System.out.println("[0] Keluar");
        System.out.println("---------------------");
        System.out.print("Pilih menu> ");

        String selectedMenu = input.nextLine();

        if (selectedMenu.equals("1")) {
            showList();
        } else if (selectedMenu.equals("2")) {
            addList();
        } else if (selectedMenu.equals("3")) {
            deleteList();
        } else if (selectedMenu.equals("0")) {
            System.exit(0);
        } else {
            System.out.println("Kamu salah pilih menu!");
            backToMenu();
        }
    }

    static void backToMenu() {
        System.out.println("");
        System.out.print("Tekan [Enter] untuk kembali..");
        input.nextLine();
        clearScreen();
    }

    static void readTodoList() {
        try {
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);

            appLists.clear();
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                appLists.add(data);

            }

        } catch (FileNotFoundException e) {
            System.out.println("Error karena: " + e.getMessage());
        }
    }

    static void showList() {
        clearScreen();
        readTodoList();
        if (appLists.size() > 0) {
            System.out.println("MUSIC LIST:");
            int index = 0;
            for (String data : appLists) {
                System.out.println(String.format("[%d] %s", index, data));
                index++;
            }
        } else {
            System.out.println("Tidak ada data!");
        }

        if (!isEditing) {
            backToMenu();
        }
    }

    static void addList() {
        clearScreen();

        System.out.println("Tambahkan judul lagu");
        System.out.print("Judul: ");
        String newAppList = input.nextLine();

        try {
            // tulis file
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.append(String.format("%s%n", newAppList));
            fileWriter.close();

            System.out.println("Berhasil ditambahkan!");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan karena: " + e.getMessage());
        }
        backToMenu();
    }

    static void deleteList() {
        isEditing = true;
        showList();

        System.out.println("-----------------");
        System.out.print("Pilih Indeks> ");
        int index = Integer.parseInt(input.nextLine());

        try {
            if (index > appLists.size()) {
                throw new IndexOutOfBoundsException("Kamu memasukan data yang salah!");
            } else {

                System.out.println("Kamu akan menghapus judul lagu:");
                System.out.println(String.format("[%d] %s", index, appLists.get(index)));
                System.out.println("Apa kamu yakin?");
                System.out.print("Jawab (y/t): ");
                String jawab = input.nextLine();

                if (jawab.equalsIgnoreCase("y")) {
                    appLists.remove(index);

                    try {
                        FileWriter fileWriter = new FileWriter(fileName, false);

                        for (String data : appLists) {
                            fileWriter.append(String.format("%s%n", data));
                        }
                        fileWriter.close();

                        System.out.println("Berhasil dihapus!");
                    } catch (IOException e) {
                        System.out.println("Terjadi kesalahan karena: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        isEditing = false;
        backToMenu();
    }

}