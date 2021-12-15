package ru.vsu.cs.dolzhenkoms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class City {
    public static HashMap<String, Flat> getLowCostFlatsByDistricts(HashMap<String, List<Flat>> separatedFlats, int roomCounts, int flatSqaure) {
        HashMap<String, Flat> lowCostFlats = new HashMap<String, Flat>();

        for (String district: separatedFlats.keySet()) {
            var filteredFlats = separatedFlats.get(district).stream().filter(f -> f.getCountOfRooms() == roomCounts).filter(f -> f.getFlatSquare() >= flatSqaure).sorted().toArray();
            if(filteredFlats.length > 0) {
                lowCostFlats.put(district, (Flat) filteredFlats[0]);
            }
        }

        return lowCostFlats;
    }

    public static HashMap<String, List<Flat>> separateFlatsByDistricts(List<Flat> flats) {
        HashMap<String, List<Flat>> separateFlats = new HashMap<String, List<Flat>>();

        for(int i = 0; i < flats.size(); i++) {
            Flat currFlat = flats.get(i);

            if(separateFlats.containsKey(currFlat.getDistrict())) {
                separateFlats.get(currFlat.getDistrict()).add(currFlat);
            }
            else {
                separateFlats.put(currFlat.getDistrict(), new ArrayList<Flat>(Arrays.asList(currFlat)));
            }
        }

        return separateFlats;
    }
}
