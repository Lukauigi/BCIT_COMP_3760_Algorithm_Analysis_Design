package ca.bcit.comp3760.labs.lab07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Three separate greedy algorithms choosing as many meeting requests as possible based on three different best ranks:
 * #1 earliest start times, #2 shortest lengths, #3 earliest end times.
 *
 * @version April 2022
 * @author Lukasz Bednarek
 * Student ID: A01206494
 * Set: D
 */
public class Lab7 {

    public static final String DATA_ONE_FILE_PATH = "src/ca/bcit/comp3760/labs/lab07/data1.txt";
    public static final String DATA_TWO_FILE_PATH = "src/ca/bcit/comp3760/labs/lab07/data2.txt";
    public static final String DATA_THREE_FILE_PATH = "src/ca/bcit/comp3760/labs/lab07/data3.txt";
    public static final String DATA_FOUR_FILE_PATH = "src/ca/bcit/comp3760/labs/lab07/data4.txt";

    /**
     * Reads two lines from a text file and returns its contents in an array of Strings.
     *
     * @param file a File
     * @return Integer data in an Arraylist
     * @throws FileNotFoundException if file cannot be found/read
     */
    private static ArrayList<Meeting> readData(final File file) throws FileNotFoundException {
        final Scanner fileReader = new Scanner(file);
        ArrayList<Meeting> meetings = new ArrayList<>();

        String name;
        int startTime;
        int endTime;

        while (fileReader.hasNext()) {
            name = fileReader.nextLine(); // get name from line
            startTime = fileReader.nextInt(); // get first number
            endTime = fileReader.nextInt(); // get second number
            meetings.add(new Meeting(name, startTime, endTime));

            // if there is more to the file, move to the next line for proper reading
            if (fileReader.hasNext()) {
                fileReader.nextLine();
            }
        }

        fileReader.close();
        return meetings;
    }

    /* Bubble sort of meeting's start times. */
    private static ArrayList<Meeting> startTimeSort(final ArrayList<Meeting> meetings) {
        int length = meetings.size();
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (meetings.get(j).getStart() > meetings.get(j + 1).getStart()) {
                    Collections.swap(meetings, j, j + 1);
                }
            }
        }
        return meetings;
    }

    /* Bubble sort of meeting's shortest lengths. */
    private static ArrayList<Meeting> shortestLengthSort(final ArrayList<Meeting> meetings) {
        int length = meetings.size();
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (meetings.get(j).getLength() > meetings.get(j + 1).getLength()) {
                    Collections.swap(meetings, j, j + 1);
                }
            }
        }
        return meetings;
    }

    /* Bubble sort of meeting's end times. */
    private static ArrayList<Meeting> endTimeSort(final ArrayList<Meeting> meetings) {
        int length = meetings.size();
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (meetings.get(j).getEnd() > meetings.get(j + 1).getEnd()) {
                    Collections.swap(meetings, j, j + 1);
                }
            }
        }
        return meetings;
    }

    /* Greedy algorithm to choose sorted meeting requests. Returns the chosen meetings. */
    private static ArrayList<Meeting> chooseMeetings(final ArrayList<Meeting> sortedRequests) {
        ArrayList<Meeting> chosenMeetings = new ArrayList<>();

        /* for every meeting request in list (pre-sorted)
        * if it does not overlap with any chosen meeting
        * add the request to the chosen meetings list.
        * */
        for (Meeting request : sortedRequests) {
            boolean overlaps = false;

            for (Meeting meeting : chosenMeetings) {
                if (request.overlapsWith(meeting)) {
                    overlaps = true;
                    break;
                }
            }

            if (!overlaps) {
                chosenMeetings.add(request);
            }
        }

        return chosenMeetings;
    }

    /* Method to choose meetings by length with a greedy algorithm.  */
    public static void chooseMeetingsByGreedyLength(final String filename) throws FileNotFoundException {
        // read meeting data from file and convert it into a list of Meeting structures
        final File dataFile = new File(filename);
        final ArrayList<Meeting> requests = readData(dataFile);

        // sort requests by shortest length
        final ArrayList<Meeting> sortedRequests = shortestLengthSort(requests);
        // greedily choose meetings
        final ArrayList<Meeting> chosenMeetings = chooseMeetings(sortedRequests);

        // print results
        System.out.printf("Number of meetings scheduled with Greedy Algorithm #2 (rank by Start): %d\n",
                chosenMeetings.size());
        System.out.println("The names of the meetings scheduled with Greedy Algorithm #2 (rank by Start):");
        for (Meeting meeting : chosenMeetings) {
            System.out.println(meeting.toString());
        }
        System.out.println();
    }

    /* Method to choose meetings by end time with a greedy algorithm.  */
    public static void chooseMeetingsByGreedyEndTime(final String filename) throws FileNotFoundException {
        // read meeting data from file and convert it into a list of Meeting structures
        final File dataFile = new File(filename);
        final ArrayList<Meeting> requests = readData(dataFile);

        // sort requests by earliest end time
        final ArrayList<Meeting> sortedRequests = endTimeSort(requests);
        // greedily choose meetings
        final ArrayList<Meeting> chosenMeetings = chooseMeetings(sortedRequests);

        // print results
        System.out.printf("Number of meetings scheduled with Greedy Algorithm #3 (rank by Start): %d\n",
                chosenMeetings.size());
        System.out.println("The names of the meetings scheduled with Greedy Algorithm #3 (rank by Start):");
        for (Meeting meeting : chosenMeetings) {
            System.out.println(meeting.toString());
        }
        System.out.println();
    }

    /* Method to choose meetings by start time with a greedy algorithm.  */
    public static void chooseMeetingsByGreedyStart(final String filename) throws FileNotFoundException {
        // read meeting data from file and convert it into a list of Meeting structures
        final File dataFile = new File(filename);
        final ArrayList<Meeting> requests = readData(dataFile);

        // sort requests by earliest start times
        final ArrayList<Meeting> sortedRequests = startTimeSort(requests);
        // greedily choose meetings
        final ArrayList<Meeting> chosenMeetings = chooseMeetings(sortedRequests);

        // print results
        System.out.printf("Number of meetings scheduled with Greedy Algorithm #1 (rank by Start): %d\n",
                chosenMeetings.size());
        System.out.println("The names of the meetings scheduled with Greedy Algorithm #1 (rank by Start):");
        for (Meeting meeting : chosenMeetings) {
            System.out.println(meeting.toString());
        }
        System.out.println();
    }

    /**
     * Drives the program.
     *
     * @param args a string, not used
     * @throws FileNotFoundException if file is not found in path
     */
    public static void main(final String[] args) throws FileNotFoundException {
        try {
            chooseMeetingsByGreedyStart(DATA_FOUR_FILE_PATH);
            chooseMeetingsByGreedyLength(DATA_FOUR_FILE_PATH);
            chooseMeetingsByGreedyEndTime(DATA_FOUR_FILE_PATH);

            File json = new File("src/ca/bcit/comp3760/labs/lab07/public-art.json");
            System.out.println(json.getAbsolutePath());
            System.out.println(json.exists());
            System.out.println(new File("").getAbsolutePath());

            File folder = new File(".").getAbsoluteFile();
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile()) {
                    System.out.println(file.getName());
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File could not be processed from path.");
        }
    }

}
