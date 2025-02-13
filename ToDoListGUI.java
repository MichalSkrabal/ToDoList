import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class ToDoListGUI {
    private static final String FILE_NAME = "tasks.txt";
    private JFrame frame;
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private JTextField taskInput;

    public ToDoListGUI() {
        frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Model pro seznam úkolů
        listModel = new DefaultListModel<>();
        loadTasks(); // Načte úkoly ze souboru

        // Grafické prvky
        taskList = new JList<>(listModel);
        taskInput = new JTextField();
        JButton addButton = new JButton("➕ Přidat úkol");
        JButton deleteButton = new JButton("🗑 Smazat úkol");

        // Panel pro zadávání úkolu
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        // Panel pro tlačítko mazání
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);

        // Přidání komponent do okna
        frame.add(new JScrollPane(taskList), BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Akce tlačítka "Přidat úkol"
        addButton.addActionListener(e -> addTask());

        // Akce tlačítka "Smazat úkol"
        deleteButton.addActionListener(e -> deleteTask());

        frame.setVisible(true);
    }

    private void addTask() {
        String task = taskInput.getText().trim();
        if (!task.isEmpty()) {
            listModel.addElement(task);
            taskInput.setText("");
            saveTasks(); // Uloží úkoly do souboru
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            listModel.remove(selectedIndex);
            saveTasks(); // Uloží změny do souboru
        }
    }

    private void saveTasks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < listModel.size(); i++) {
                writer.println(listModel.get(i));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Chyba při ukládání souboru!", "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTasks() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    listModel.addElement(line);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Chyba při načítání souboru!", "Chyba", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoListGUI::new);
    }
}

