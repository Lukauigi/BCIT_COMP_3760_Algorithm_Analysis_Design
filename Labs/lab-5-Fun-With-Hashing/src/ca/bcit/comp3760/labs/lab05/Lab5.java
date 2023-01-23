package ca.bcit.comp3760.labs.lab05;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * Program to count Hashing Collisions.
 *
 * @version March 2022
 * @author Lukasz Bednarek
 * Student ID: A01206494
 * Set: 3D
 */
public class Lab5 {

    /**
     * String value of path to 37_names.txt.
     */
    public static final String FILE_37_NAMES = "src/ca/bcit/comp3760/labs/lab05/37_names.txt";
    /**
     * String value of path to 333_names.txt.
     */
    public static final String FILE_333_NAMES = "src/ca/bcit/comp3760/labs/lab05/333_names.txt";
    /**
     * String value of path to 5163_names.txt.
     */
    public static final String FILE_5163_NAMES = "src/ca/bcit/comp3760/labs/lab05/5163_names.txt";
    /**
     * Value of ASCII character before letter A.
     */
    public static final int ASCII_CAPITAL_LETTER_THRESHOLD = 'A' - 1;
    /**
     * Hashing number to multiply values.
     */
    public static final int HASHING_NUMBER_H2 = 26;
    /**
     * Hashing base value.
     */
    public static final int BASE_HASH_NUMBER_H3 = 17;
    /**
     * Hashing number to multiply values.
     */
    public static final int HASHING_MULTIPLE_H3 = 37;

    /**
     * Executes a closed hashing linear probing hashing method and returns the number of collisions.
     *
     * @param words an array of Strings
     * @param tableSize an int
     * @param hashMethod a Method
     * @return collision count at end of process
     * @throws InvocationTargetException if name of method trying to invoke is a bad reference
     * @throws IllegalAccessException if function cannot gain access to method
     */
    private static int closedHashCollisions(final String[] words, final int tableSize, final Method hashMethod)
            throws InvocationTargetException, IllegalAccessException {
        int count = 0;
        final String[] table = new String[tableSize];

        for (String word : words) {
            /* Calls the hash function and stores the allocated index position */
            int positionHash = (int) hashMethod.invoke(Lab5.class, word, tableSize);
            int nextIndex = 0;

            /* Checks if index is table is occupied */
            if (table[positionHash] == null) {
                table[positionHash] = word;
            } else {
                ++count;
                /* If next index is still within range of table, set position to next index
                * else we start looking for an empty index at 0. */
                if (positionHash + 1 < tableSize - 1) {
                    nextIndex = positionHash + 1;
                }

                /* If they are the same then we have looped through the whole table */
                while (nextIndex != positionHash) {
                    if (table[nextIndex] == null) {
                        table[nextIndex] = word;
                        /* Placed value, the loop's purpose is done */
                        break;
                    }

                    /* Loop back to start of table if we will reach the end */
                    if (nextIndex == tableSize - 1) {
                        nextIndex = 0;
                    } else {
                        ++nextIndex;
                    }
                }
            }
        }

        return count;
    }

    /**
     * Reads a line from a text file and returns its contents in an array of Strings.
     *
     * @param file a File
     * @return Integer data in an Arraylist
     * @throws FileNotFoundException if file cannot be found/read
     */
    private static String[] readData(final File file) throws FileNotFoundException {
        final Scanner fileReader = new Scanner(file);
        String line;

        /* Dumps line from file into array. */
        line = fileReader.next();
        final String[] words = line.split(",");

        fileReader.close();
        return words;
    }

    /**
     * Returns the hash value of a word (Method 1).
     *
     * Method 1: Adds all the sums of each hashed letter
     * and gets the remainder of it (divided by size).
     *
     * @param string a String
     * @param N an int
     * @return hash value of word
     */
    public static int H1(final String string, final int N) {
        int sum = 0;
        for (int i = 0; i < string.length(); i++) {
            sum += string.charAt(i) - ASCII_CAPITAL_LETTER_THRESHOLD;
        }
        return sum % N;
    }

    /**
     * Returns the hash value of a word (Method 2).
     *
     * Method 2: Adds all the sums of each hashed letter times the power of 26,
     * and gets the remainder of it (divided by size).
     *
     * @param string a String
     * @param N an int
     * @return hash value of word
     */
    public static int H2(final String string, final int N) {
        long sum = 0;
        for (int i = 0; i < string.length(); i++) {
            sum += (string.charAt(i) - ASCII_CAPITAL_LETTER_THRESHOLD) * Math.pow(HASHING_NUMBER_H2, i);
        }
        return (int) (sum % N);
    }

    /**
     * Returns the hash value of a word (Method 3).
     *
     * Method 3: each letter value is added to base hashing number and multiplied by another hashing number,
     * and gets the remainder of it (divided by size). Hashing numbers are prime numbers to try to
     * achieve unique hashing of strings.
     *
     * Hashing function is reminiscent to my class did in last term's OOP class.
     *
     * @param string a String
     * @param N an int
     * @return hash value of word
     */
    public static int H3(final String string, final int N) {
        long result = BASE_HASH_NUMBER_H3;
        for (int i = 0; i < string.length(); i++) {
            result += (string.charAt(i) - ASCII_CAPITAL_LETTER_THRESHOLD) + HASHING_MULTIPLE_H3 * BASE_HASH_NUMBER_H3;
        }
        return (int) (result % N);
    }

    /**
     * Executes hashing on a given filename.
     *
     * @param filename a String
     * @throws FileNotFoundException if file cannot be found/read
     * @throws NoSuchMethodException if method in Lab 5 does not exist
     * @throws InvocationTargetException if name of method trying to invoke is a bad reference
     * @throws IllegalAccessException if function cannot gain access to method
     */
    public static void DoTheStuff(final String filename) throws FileNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        final File dataFile = new File(filename);
        final String[] fileData = readData(dataFile);

        /* Turning these methods into callbacks for collision counting function up next */
        Method h1 = Lab5.class.getMethod("H1", String.class, int.class);
        Method h2 = Lab5.class.getMethod("H2", String.class, int.class);
        Method h3 = Lab5.class.getMethod("H3", String.class, int.class);

        /* Get collision counts of each hashing method.
        * To get different 2N, 5N, and 10N table array, times the fileData.length by 2, 5, or 10.  */
        final int collisionCountH1 = closedHashCollisions(fileData, 10*fileData.length, h1);
        final int collisionCountH2 = closedHashCollisions(fileData, 10*fileData.length, h2);
        final int collisionCountH3 = closedHashCollisions(fileData, 10*fileData.length, h3);

        final int[] collisionTallies = new int[]{collisionCountH1, collisionCountH2, collisionCountH3};

        System.out.println(filename + " Collision Counts:");
        for (int tally : collisionTallies) {
            System.out.println(tally);
        }

    }

    /**
     * Drives the program.
     *
     * @param args a String, not used
     * @throws FileNotFoundException if file is not found/read
     * @throws NoSuchMethodException if method in Lab 5 does not exist
     */
    public static void main(final String[] args) throws FileNotFoundException, NoSuchMethodException {
        try {
            DoTheStuff(FILE_37_NAMES);
            DoTheStuff(FILE_333_NAMES);
            DoTheStuff(FILE_5163_NAMES);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File could not be read.");
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodException("Method was not found.");
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
