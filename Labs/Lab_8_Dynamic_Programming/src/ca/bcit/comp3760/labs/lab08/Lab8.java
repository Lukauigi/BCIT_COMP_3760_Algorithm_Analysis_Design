package ca.bcit.comp3760.labs.lab08;

/**
 * Making 2 (recursive and dynamic programming) algorithms to find how many routes is there to get
 * to the destination. Blocks m (blocks down) and n (blocks across) are the parameters of the
 * algorithm defining the boundaries of the whole graph.
 *
 * The starting point is (0, 0) top-left point of the graph and the destination are (m, n) the
 * bottom-right of the graph.
 *
 * @version April 2022
 * @author Lukasz Bednarek
 * Student ID: A01206494
 * Set: D
 */
public class Lab8 {

    public static final int UPPER_THRESHOLD_FOR_RECURSIVE_ALGORITHM = 18;
    public static final int UPPER_THRESHOLD_FOR_DYNAMIC_PROGRAMMING_ALGORITHM = 31;

    /* Finds the number of ways to walk from the source (0, 0) to the destination, recursively. */
    private static long SW_Recursive(final int m, final int n) {
        if (m == 0) { //found end of path, return 1
            return 1;
        } else if (n == 0) { //found end of path, return 1
            return 1;
        } else if (m > 0 && n > 0) { //essentially walk backwards from left and up and repeat process
            return SW_Recursive(m - 1, n) + SW_Recursive(m, n - 1);
        }
        return -1; //returns -1 if m or n are a negative number. To indicate an error.
    }

    /* Finds the number of ways to walk from the source (0, 0) to the destination, with dynamic programming method. */
    private static long SW_DynamicProg(final int m, final int n) {
        long[][] routeMap = new long[m + 1][n + 1]; // create map of blocks b/w home (source) & school (destination)
        routeMap[0][0] = 1; // only 1 wat to get nowhere

        // initialize top vertices [(0, 1), (0, 2)... (0, n)] to 1 since there is only 1 way to get there
        for (int i = 1; i <= n; ++i) {
            routeMap[0][i] = 1;
        }

        // initialize left vertices [(1, 0), (2, 0)... (n, 0)] to 1 since there is only 1 way to get there
        for (int j = 1; j <= m; ++j) {
            routeMap[j][0] = 1;

            // maps the # of ways to get to a vertex
            for (int k = 1; k <= n; ++k) {
                // maps the value of [j][k] by taking values above it and left of it to determine ways to get there
                routeMap[j][k] = routeMap[j][k - 1] + routeMap[j - 1][k];
            }
        }
        return routeMap[m][n];
    }

    /**
     * Drives the program.
     *
     * @param args a string, not used
     */
    public static void main(final String[] args) {
        int m = 0;
        int n = 0;
        long start;
        long end;

        // calculations for recursive algorithm from parameters (0, 0) -> (17, 17)
        while (m < UPPER_THRESHOLD_FOR_RECURSIVE_ALGORITHM && n < UPPER_THRESHOLD_FOR_RECURSIVE_ALGORITHM) {

            start = System.currentTimeMillis();
            System.out.printf("SW_Recursive (%d, %d) = %d,", m, n, SW_Recursive(m, n));
            end = System.currentTimeMillis();
            System.out.printf("time is %d ms\n", end - start);

            ++m;
            ++n;
        }

        System.out.println();

        m = 0;
        n = 0;

        // calculations for recursive algorithm from parameters (0, 0) -> (30, 30)
        while (m < UPPER_THRESHOLD_FOR_DYNAMIC_PROGRAMMING_ALGORITHM
                && n < UPPER_THRESHOLD_FOR_DYNAMIC_PROGRAMMING_ALGORITHM) {

            start = System.currentTimeMillis();
            System.out.printf("SW_DynamicProg (%d, %d) = %d,", m, n, SW_DynamicProg(m, n));
            end = System.currentTimeMillis();
            System.out.printf("time is %d ms\n", end - start);

            ++m;
            ++n;
        }
    }

}
