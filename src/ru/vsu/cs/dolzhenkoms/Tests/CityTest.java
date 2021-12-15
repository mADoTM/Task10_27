package ru.vsu.cs.dolzhenkoms.Tests;

import org.junit.Assert;
import org.junit.Test;
import ru.vsu.cs.dolzhenkoms.City;
import ru.vsu.cs.dolzhenkoms.Flat;
import ru.vsu.cs.dolzhenkoms.FlatParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CityTest {
    @Test
    public void getLowCostFlatsByDistricts() {
        HashMap<String, Flat> exptectedArray = new HashMap<String, Flat>();
        exptectedArray.put("Ленинский", new Flat("Ленинский", 4, 213, 16, 2020));

        List<Flat> flats = new ArrayList<Flat>();
        flats.add(FlatParser.parseLine("Ленинский 4 213 16 2020"));
        flats.add(FlatParser.parseLine("Советский 6 213 16 2020"));

        var testArray = City.getLowCostFlatsByDistricts(City.separateFlatsByDistricts(flats),4,10);

        Assert.assertEquals(testArray.toString(), exptectedArray.toString());
    }

    @Test
    public void getLowCostFlatsByDistricts2() {
        HashMap<String, Flat> exptectedArray = new HashMap<String, Flat>();

        List<Flat> flats = new ArrayList<Flat>();
        flats.add(FlatParser.parseLine("Ленинский 4 213 16 2020"));
        flats.add(FlatParser.parseLine("Советский 6 213 16 2020"));

        var testArray = City.getLowCostFlatsByDistricts(City.separateFlatsByDistricts(flats),4,260);

        Assert.assertEquals(testArray.toString(), exptectedArray.toString());
    }

    @Test
    public void getLowCostFlatsByDistricts3() {
        HashMap<String, Flat> exptectedArray = new HashMap<String, Flat>();
        exptectedArray.put("Ленинский", new Flat("Ленинский", 4, 213, 16, 2020));

        List<Flat> flats = new ArrayList<Flat>();
        flats.add(FlatParser.parseLine("Ленинский 4 213 16 2020"));
        flats.add(FlatParser.parseLine("Северный 18 623 40 2023230"));
        flats.add(FlatParser.parseLine("Советский 10 341 30 232302"));
        flats.add(FlatParser.parseLine("Советский 6 213 16 2020"));
        flats.add(FlatParser.parseLine("Левобережный 1 324 3213 12321"));

        var testArray = City.getLowCostFlatsByDistricts(City.separateFlatsByDistricts(flats), 4, 10);

        Assert.assertEquals(testArray.toString(), exptectedArray.toString());
    }

    @Test
    public void getLowCostFlatsByDistricts4() {
        HashMap<String, Flat> exptectedArray = new HashMap<String, Flat>();

        List<Flat> flats = new ArrayList<Flat>();
        flats.add(FlatParser.parseLine("Ленинский 4 213 16 2020"));
        flats.add(FlatParser.parseLine("Советский 6 213 16 2020"));

        var testArray = City.getLowCostFlatsByDistricts(City.separateFlatsByDistricts(flats),5,10);

        Assert.assertEquals(testArray.toString(), exptectedArray.toString());
    }
}
