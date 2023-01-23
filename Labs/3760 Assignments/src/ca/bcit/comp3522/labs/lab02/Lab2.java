package ca.bcit.comp3522.labs.lab02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Brute force algorithm to find a multiple of 37 from a triple (3 separate numbers added up)
 * in an array whose data is pulled from a text file.
 *
 * @version January 2022
 * @author Lukasz Bednarek
 * Student ID: A01206494
 * Set: D
 */
public final class Lab2 {

    /**
     * String value of path to data0.txt.
     */
    public static final String DATA_ZERO_FILENAME = "src/ca/bcit/comp3522/labs/lab02/data0.txt";
    /**
     * String value of path to data1.txt.
     */
    public static final String DATA_ONE_FILENAME = "src/ca/bcit/comp3522/labs/lab02/data1.txt";
    /**
     * String value of path to data2.txt.
     */
    public static final String DATA_TWO_FILENAME = "src/ca/bcit/comp3522/labs/lab02/data2.txt";
    /**
     * String value of path to data3.txt.
     */
    public static final String DATA_THREE_FILENAME = "src/ca/bcit/comp3522/labs/lab02/data3.txt";
    /**
     * String value of path to data4.txt.
     */
    public static final String DATA_FOUR_FILENAME = "src/ca/bcit/comp3522/labs/lab02/data4.txt";
    /**
     * String value of path to data5.txt.
     */
    public static final String DATA_FIVE_FILENAME = "src/ca/bcit/comp3522/labs/lab02/data5.txt";
    /**
     * String value of path to data6.txt.
     */
    public static final String DATA_SIX_FILENAME = "src/ca/bcit/comp3522/labs/lab02/data6.txt";
    /**
     * Integer value thirty-seven which a triple must be a multiple of.
     */
    public static final int THIRTY_SEVEN_TRIPLE_MULTIPLE = 37;

    /**
     * Calculates three separate integer data values in the array and looks for the combination
     * where the three integers added together are a multiple of thirty-seven.
     *
     * @param data an ArrayList of Integer
     * @param fileName a string
     */
    private static void findTripleOfMultiple(final ArrayList<Integer> data, final String fileName) {
        /* Every For loop looks at a different number (index) */
        for (int i = 0; i < data.size() - 2; i++) {
            for (int j = i + 1; j < data.size() - 1; j++) {
                for (int k = j + 1; k < data.size(); k++) {

                    int triple = data.get(i) + data.get(j) + data.get(k);
                    /* if the sum is a multiple (divisible without remainder) of 37 */
                    if (triple % THIRTY_SEVEN_TRIPLE_MULTIPLE == 0) {
                        System.out.printf("""
                                            From file %s
                                            -----------------
                                            Numbers: %d, %d, %d added together are a multiple of 37.
                                             \s
                                             """,
                                fileName, data.get(i), data.get(j), data.get(k));
                    }
                }
            }
        }
    }

    /**
     * Reads an integer value each line from a text file and returns its contents in an Arraylist.
     *
     * @param file a File
     * @return Integer data in an Arraylist
     * @throws FileNotFoundException if file cannot be found
     */
    private static ArrayList<Integer> readData(final File file) throws FileNotFoundException {
        final Scanner fileReader = new Scanner(file);
        final ArrayList<Integer> numericData = new ArrayList<>();

        while (fileReader.hasNextInt()) {
            numericData.add(fileReader.nextInt());
        }

        fileReader.close();
        return numericData;
    }

    /**
     * Executes a series of methods to find a triple which is a multiple of thirty-seven.
     *
     * @param fileName a string
     * @throws FileNotFoundException if file cannot be found
     */
    static void doTheStuff(final String fileName) throws FileNotFoundException {
        final File dataFile = new File(fileName);
        final ArrayList<Integer> fileData = readData(dataFile); // put contents of file into array
        findTripleOfMultiple(fileData, fileName);
    }

    /**
     * Drives the program.
     *
     * @param args a string, not used
     * @throws FileNotFoundException if file is not found
     */
    public static void main(final String[] args) throws FileNotFoundException {
        try {
            doTheStuff(DATA_ZERO_FILENAME);
            doTheStuff(DATA_ONE_FILENAME);
            doTheStuff(DATA_TWO_FILENAME);
            doTheStuff(DATA_THREE_FILENAME);
            doTheStuff(DATA_FOUR_FILENAME);
            doTheStuff(DATA_FIVE_FILENAME);
            doTheStuff(DATA_SIX_FILENAME);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File could not be found.");
        }
    }

}
