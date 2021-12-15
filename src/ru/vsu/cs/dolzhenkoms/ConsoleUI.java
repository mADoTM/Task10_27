package ru.vsu.cs.dolzhenkoms;

import ru.vsu.cs.dolzhenkoms.Utils.FileManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private String inputFile;
    private String outputFile;

    private ConsoleCommand status = ConsoleCommand.Help;

    public void run() throws IOException {
        switch(status) {
            case Help:
                executeHelpCommand();
                parseArgsFromString();
                break;
            case Exit:
                executeExitCommand();
                break;
            case Error:
                executeErrorCommand();
                break;
            case RunLogic:
                executeRunLogicCommand();
                break;
        }
        run();
    }

    private void parseArgsFromString() {
        System.out.println("Введите команду, которая указана в справке:");
        Scanner scn = new Scanner(System.in);
        String line = scn.nextLine();
        String[] args = line.split(" ");

        if(args.length == 0) {
            status = ConsoleCommand.Error;
            return;
        }

        for(int i = 0; i < args.length; i++) {
            if(args[0].equals("--help")) {
                status = ConsoleCommand.Help;
                return;
            }
            if(args[0].equals("--exit")) {
                status = ConsoleCommand.Exit;
                return;
            }
            if(args[i].startsWith("--input-file=")) {
                inputFile = args[i].replace("--input-file=","");
            }
            else if(args[i].startsWith("--output-file=")) {
                outputFile = args[i].replace("--output-file=","");
            }
            else {
                status = ConsoleCommand.Error;
                return;
            }
        }
        if(inputFile != null && outputFile != null) {
            status = ConsoleCommand.RunLogic;
        }
    }

    private void executeHelpCommand() {
        System.out.println("Справка:");
        System.out.println("--help              Справка");
        System.out.println("--exit              Выход из программы");
        System.out.println("--input-file=       Название файла-ввода");
        System.out.println("--output-file=      Название файла-вывода");
    }

    private void executeExitCommand() {
        System.out.println("Выход из программы...");
        System.exit(0);
    }

    private void executeErrorCommand() {
        System.out.println("Вы ввели неправильный формат аргументов, повторите заново.");

        status = ConsoleCommand.Help;
    }

    private void executeRunLogicCommand() throws IOException {
        List<String> fileLinesFileManager = FileManager.readAllLinesFromFile(inputFile);
        List<Flat> flats = FlatParser.parseLines(fileLinesFileManager);

        int filterForRooms = readInt("количество комнат");
        int filterForSquare = readInt("площадь квартиры");
        var separatedFlats = City.separateFlatsByDistricts(flats);

        var lowerCostFlats = City.getLowCostFlatsByDistricts(separatedFlats, filterForRooms, filterForSquare);


        System.out.println("Подходящие квартиры");
        for(String district: lowerCostFlats.keySet()) {
            System.out.println(lowerCostFlats.get(district).toString());
        }

        saveFile(lowerCostFlats);

        status = ConsoleCommand.Exit;
    }

    private int readInt(String text) {
        Scanner scn = new Scanner(System.in);
        int value = 0;
        try {
            System.out.print("Введите " + text + " - ");
            value = scn.nextInt();
        }
        catch (Exception ex) {
            System.out.println("Вы ввели не число! Повторите заново!");
            return readInt(text);
        }

        return value;
    }

    private void saveFile(HashMap<String, Flat> flats) {
        StringBuilder textForFile = new StringBuilder();

        for(String district: flats.keySet()) {
            textForFile.append(flats.get(district).toString());
        }

        try {
            FileManager.createFileWithText(outputFile, textForFile.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
