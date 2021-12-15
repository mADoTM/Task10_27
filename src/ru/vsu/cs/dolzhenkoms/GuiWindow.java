package ru.vsu.cs.dolzhenkoms;
import ru.vsu.cs.dolzhenkoms.Utils.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuiWindow extends JFrame {
    private JTable inputTable;
    private JTable outputArrayTable;

    private JLabel countOfRoomsLabel;
    private JLabel flatSquareLabel;

    private JTextArea countOfRoomsTextArea;
    private JTextArea flatSquareTextArea;

    private JButton openButton;
    private JButton saveButton;
    private JButton executeButton;
    private JButton addRowTableButton;
    private JButton removeRowTableButton;

    private Object[][] defaultArray = new Object[][] { FlatParser.toObjectArray(new Flat("Ленинский", 2, 60, 3, 2000)) };

    private String[] tableHeaders = {"Район", "Количество комнат","Площадь квартиры","Площадь кухни", "Цена"};

    public GuiWindow() {
        super("WindowUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initButtons();
        initMarkup();

        setSize(600, 400);
        setVisible(true);
    }

    private void initButtons(){
        openButton = new JButton("Открыть файл");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(new JFrame());

                if(option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    System.out.println("File Selected: " + file.getName());

                    try {
                        Object[][] flatsArray = getFlatsFromFile(file.getPath());
                        fillTableByArray((DefaultTableModel) inputTable.getModel(), flatsArray);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }


                }else{
                    System.out.println("Open command canceled");
                }
            }
        });

        executeButton = new JButton("Сформировать новый массив");
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeLogic();
            }
        });

        saveButton = new JButton("Сохранить файл");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame parentFrame = new JFrame();

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");

                int userSelection = fileChooser.showSaveDialog(parentFrame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    System.out.println("Save as file: " + fileToSave.getAbsolutePath());

                    Object[][] arrayFromTable = getArrayFromTable((DefaultTableModel) outputArrayTable.getModel());

                    saveFile(arrayFromTable, fileToSave.getPath());
                }
            }
        });

        addRowTableButton = new JButton("+");
        addRowTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) inputTable.getModel();
                model.setRowCount(model.getRowCount() + 1);
            }
        });

        removeRowTableButton = new JButton("-");
        removeRowTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) inputTable.getModel();
                model.setRowCount(model.getRowCount() - 1);
            }
        });

    }

    private void initMarkup() {
        DefaultTableModel inputModel = new DefaultTableModel(defaultArray, tableHeaders);
        inputTable = new JTable(inputModel);

        JScrollPane inputScrollPane = new JScrollPane(inputTable);

        JPanel editTableButtons = new JPanel();
        editTableButtons.add(addRowTableButton);
        editTableButtons.add(removeRowTableButton);

        JPanel mainButtons = new JPanel();
        mainButtons.add(openButton);
        mainButtons.add(executeButton);
        mainButtons.add(saveButton);

        countOfRoomsLabel = new JLabel("Количество комнат");
        countOfRoomsTextArea = new JTextArea();
        Box countOfRoomsBox = new Box(BoxLayout.X_AXIS);
        countOfRoomsBox.add(countOfRoomsLabel);
        countOfRoomsBox.add(countOfRoomsTextArea);

        flatSquareLabel = new JLabel("Мин. площадь");
        flatSquareTextArea = new JTextArea();
        Box flatSquareBox = new Box(BoxLayout.X_AXIS);
        flatSquareBox.add(flatSquareLabel);
        flatSquareBox.add(flatSquareTextArea);

        DefaultTableModel defaultOutputModel = new DefaultTableModel(defaultArray, tableHeaders);
        outputArrayTable = new JTable(defaultOutputModel);
        JScrollPane outputScrollPane = new JScrollPane(outputArrayTable);

        Box mainBox = new Box(BoxLayout.Y_AXIS);
        mainBox.add(inputScrollPane);
        mainBox.add(editTableButtons);
        mainBox.add(countOfRoomsBox);
        mainBox.add(flatSquareBox);
        mainBox.add(mainButtons);
        mainBox.add(outputScrollPane);


        getContentPane().add(mainBox);
    }

    private Object[][] getFlatsFromFile(String path) throws IOException {
        var fileLines = FileManager.readAllLinesFromFile(path);

        Object[][] flats = new Object[fileLines.size()][];

        for(int i = 0; i < fileLines.size(); i++) {
            Flat flat = FlatParser.parseLine(fileLines.get(i));
            flats[i] = FlatParser.toObjectArray(flat);
        }

        return flats;
    }

    private void fillTableByArray(DefaultTableModel model,Object[][] array) {
        model.setRowCount(0);

        for(int i = 0; i < array.length; i++) {
            model.addRow(array[i]);
        }
    }

    private Object[][] getArrayFromTable(DefaultTableModel model) {
        Object[][] array = new Object[model.getRowCount()][5];

        for(int i = 0; i < model.getRowCount(); i++) {
            for(int j = 0; j < 5; j++) {
                array[i][j] = model.getValueAt(i,j);
            }
        }

        return array;
    }

    private void executeLogic() {
        int countOfRooms = Integer.parseInt(countOfRoomsTextArea.getText());
        int flatSquare = Integer.parseInt(flatSquareTextArea.getText());

        Object[][] arrayFromTable = getArrayFromTable((DefaultTableModel) inputTable.getModel());

        List<Flat> flats = new ArrayList<>();
        for(int i = 0; i < arrayFromTable.length; i++) {
            flats.add(FlatParser.toFlatFromObjectArray(arrayFromTable[i]));
        }

        var separatedFlats = City.separateFlatsByDistricts(flats);
        var lowerCostFlats = City.getLowCostFlatsByDistricts(separatedFlats, countOfRooms, flatSquare);

        Object[][] outputArray = new Object[lowerCostFlats.size()][5];
        int i = 0;
        for(String district: lowerCostFlats.keySet()) {
            outputArray[i] = FlatParser.toObjectArray(lowerCostFlats.get(district));
            i++;
        }

        fillTableByArray((DefaultTableModel) outputArrayTable.getModel(), outputArray);
    }

    private void saveFile(Object[][] array, String outputFile) {
        StringBuilder textForFile = new StringBuilder();

        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < array[i].length; j++) {
                textForFile.append(array[i][j] + " ");
            }
            textForFile.append("\n");
        }

        try {
            FileManager.createFileWithText(outputFile, textForFile.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
