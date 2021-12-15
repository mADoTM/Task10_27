package ru.vsu.cs.dolzhenkoms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FlatParser {
    public static Flat parseLine(String line) {
        Flat flat = new Flat();
        String[] args = line.split(" ");
        if(args.length < 5) {
            return new Flat("Ленинский", 2, 60, 3, 2000);
        }
        else {
            flat.setDistrict(args[0]);
            flat.setCountOfRooms(Integer.parseInt(args[1]));
            flat.setFlatSquare(Integer.parseInt(args[2]));
            flat.setKitchenSquare(Integer.parseInt(args[3]));
            flat.setCost(Integer.parseInt(args[4]));
        }

        return flat;
    }

    public static List<Flat> parseLines(List<String> lines) {
        List<Flat> flats = new ArrayList<>();

        for(int i = 0; i < lines.size(); i++) {
            flats.add(parseLine(lines.get(i)));
        }

        return flats;
    }

    public static Object[] toObjectArray(Flat flat) {
        Object[] array = new Object[5];

        array[0] = flat.getDistrict();
        array[1] = flat.getCountOfRooms();
        array[2] = flat.getFlatSquare();
        array[3] = flat.getKitchenSquare();
        array[4] = flat.getCost();

        return array;
    }

    public static Flat toFlatFromObjectArray(Object[] array) {
        Flat flat = new Flat();

        flat.setDistrict(array[0].toString());
        flat.setCountOfRooms(Integer.parseInt(array[1].toString()));
        flat.setFlatSquare(Integer.parseInt(array[2].toString()));
        flat.setKitchenSquare(Integer.parseInt(array[3].toString()));
        flat.setCost(Integer.parseInt(array[4].toString()));

        return flat;
    }
}
