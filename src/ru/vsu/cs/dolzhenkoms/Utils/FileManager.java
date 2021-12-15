package ru.vsu.cs.dolzhenkoms.Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileManager {
    public static List<String> readAllLinesFromFile(String path) throws IOException {
        if(path == null)
            return null;

        List<String> fileLines = isFileExist(path) ? Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8) : null;

        return fileLines;
    }

    public static void createFileWithText(String filePath, String text) throws IOException {
        if(isFileExist(filePath))
            return;

        File file = new File(filePath);
        file.createNewFile();

        FileWriter fw = new FileWriter(file);
        fw.write(text);
        fw.close();
    }

    public static boolean isFileExist(String filePath) {
        if(filePath == null)
            return false;
        return (new File(filePath)).exists();
    }
}
