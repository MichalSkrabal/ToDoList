import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoListApp {
    private static final String FILE_NAME = "tasks.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> tasks = loadTasks(); // Načtení úkolů ze souboru

        while (true) {
            System.out.println("\n--- To-Do List ---");
            System.out.println("1. Přidat úkol");
            System.out.println("2. Zobrazit všechny úkoly");
            System.out.println("3. Smazat úkol");
            System.out.println("4. Ukončit program");
            System.out.print("Vyber akci: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Očistí buffer po čísle

            switch (choice) {
                case 1:
                    System.out.print("Zadej nový úkol: ");
                    String task = scanner.nextLine();
                    tasks.add(task);
                    saveTasks(tasks); // Uložení úkolu do souboru
                    System.out.println("✅ Úkol přidán!");
                    break;
                case 2:
                    System.out.println("\n📋 Seznam úkolů:");
                    if (tasks.isEmpty()) {
                        System.out.println("Žádné úkoly k zobrazení.");
                    } else {
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                    }
                    break;
                case 3:
                    System.out.println("\n🗑 Smazání úkolu:");
                    if (tasks.isEmpty()) {
                        System.out.println("Není co mazat.");
                        break;
                    }
                    System.out.print("Zadej číslo úkolu k smazání: ");
                    int taskNumber = scanner.nextInt();
                    if (taskNumber > 0 && taskNumber <= tasks.size()) {
                        tasks.remove(taskNumber - 1);
                        saveTasks(tasks); // Aktualizace souboru po smazání
                        System.out.println("✅ Úkol smazán!");
                    } else {
                        System.out.println("⚠ Neplatné číslo úkolu!");
                    }
                    break;
                case 4:
                    System.out.println("👋 Ukončuji program. Úkoly zůstávají uloženy!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("⚠ Neplatná volba. Zkus to znovu.");
            }
        }
    }

    // 📝 Ukládání úkolů do souboru
    private static void saveTasks(ArrayList<String> tasks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String task : tasks) {
                writer.println(task);
            }
        } catch (IOException e) {
            System.out.println("⚠ Chyba při ukládání úkolů: " + e.getMessage());
        }
    }

    // 📂 Načítání úkolů ze souboru při spuštění
    private static ArrayList<String> loadTasks() {
        ArrayList<String> tasks = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (file.exists()) {
            try (Scanner fileScanner = new Scanner(file)) {
                while (fileScanner.hasNextLine()) {
                    tasks.add(fileScanner.nextLine());
                }
            } catch (IOException e) {
                System.out.println("⚠ Chyba při načítání úkolů: " + e.getMessage());
            }
        }

        return tasks;
    }
}
