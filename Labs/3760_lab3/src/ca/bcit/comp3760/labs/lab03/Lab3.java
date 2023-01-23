package ca.bcit.comp3760.labs.lab03;

import java.util.ArrayList;

/**
 * Decrease and conquer algorithm to create a list of permutations of light patterns.
 *
 * @version January 2022
 * @author Lukasz Bednarek
 * Student ID: A01206494
 * Set: D
 */
public final class Lab3 {

    /**
     * The String which represents the red colour.
     */
    public static final String RED = "0";
    /**
     * The String which represents the white colour.
     */
    public static final String WHITE = "1";
    /**
     * The String which represents the green colour.
     */
    public static final String GREEN = "2";
    /**
     * The number of colours which could be represented.
     */
    public static final int PERMUTATION_RANGE = 3;
    /**
     * The number of examples for testing a function.
     */
    public static final int EXAMPLE_RANGE = 10;

    /**
     * Generates light patterns which have no same digits next to each other, to a given length.
     *
     * @param length an int
     * @return an ArrayList of Strings
     */
    private static ArrayList<String> generatePatternsWithNoDoubleDigits(final int length) {
        ArrayList<String> lightPatterns = new ArrayList<>();
        if (length == 1) {
            // base case are all options of a single char
            lightPatterns.add(RED);
            lightPatterns.add(WHITE);
            lightPatterns.add(GREEN);

            return lightPatterns;
        }

        lightPatterns = generatePatternsWithNoDoubleDigits(length - 1);

        ArrayList<String> lightPermutationsToLength = new ArrayList<>();
        for (int i = 0; i < PERMUTATION_RANGE; i++) {
            // char which will be prepended to previously found light patterns
            String currentPermutation = String.valueOf(i);
            for (int j = 0; j < lightPatterns.size(); j++) {
                // if the front digit and start of next pattern are not the same, add pattern to list
                if (currentPermutation.compareTo(lightPatterns.get(j).substring(0, 1)) != 0) {
                    // combines the digit with previous patterns found
                    lightPermutationsToLength.add(currentPermutation + lightPatterns.get(j));
                }
            }
        }

        return lightPermutationsToLength;
    }

    /**
     * Generates all possible light patterns to a given length.
     *
     * @param length an int
     * @return an ArrayList of Strings
     */
    private static ArrayList<String> generateAllPatterns(final int length) {
        ArrayList<String> allLightPatternPermutations = new ArrayList<>();
        if (length == 1) {
            // base case are all options of a single char
            allLightPatternPermutations.add(RED);
            allLightPatternPermutations.add(WHITE);
            allLightPatternPermutations.add(GREEN);

            return allLightPatternPermutations;
        }

        allLightPatternPermutations = generateAllPatterns(length - 1);

        ArrayList<String> lightPermutationsToLength = new ArrayList<>();
        for (int i = 0; i < PERMUTATION_RANGE; i++) {
            // char which will be prepended to previously found light patterns
            String currentPermutation = String.valueOf(i);
            for (int j = 0; j < allLightPatternPermutations.size(); j++) {
                // combines the digit with previous patterns found
                lightPermutationsToLength.add(currentPermutation + allLightPatternPermutations.get(j));
            }
        }

        return lightPermutationsToLength;
    }

    /**
     * Drives the program.
     *
     * @param args a String, not used
     */
    public static void main(final String[] args) {
        System.out.println("Generating all patterns of a given length:");
        for (int length = 1; length <= EXAMPLE_RANGE; length++) {
            ArrayList<String> patterns = generateAllPatterns(length);
            System.out.printf("Length %d produces %d patterns.\n", length, patterns.size());
        }

        System.out.println("\nGenerating patterns without double-digits:");
        for (int length = 1; length <= EXAMPLE_RANGE; length++) {
            ArrayList<String> patterns = generatePatternsWithNoDoubleDigits(length);
            System.out.printf("Length %d produces %d patterns.\n", length, patterns.size());
        }
    }

}
