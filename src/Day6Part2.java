import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Day6Part2 {
    static Character[][] grid;
    static int[] start;

    public static boolean inBounds(int[] point) {
        return point[0] >= 0 && point[0] < grid.length && point[1] >= 0 && point[1] < grid[0].length;
    }
    
    public static int[] nextDirection(int[] direction) {
        return new int[] {-direction[1], direction[0]};
    }


    public static boolean forever(int[] spot) {
        Character previously = grid[spot[0]][spot[1]];
        grid[spot[0]][spot[1]] = '#';

        // System.out.printf("%d %d:\n", spot[0], spot[1]);

        HashMap<String, Boolean> pounds = new HashMap<>();

        int[] direction = {0, -1};
        int[] current = new int[] {start[0], start[1]};
        // int[] lastVisited = new int[2];

        while (inBounds(current)) {
            // System.out.printf("%d %d)\n", current[0], current[1]);

            if (Character.compare(grid[current[0]][current[1]], '#') == 0) {
                String key = current[0] + "," + current[1]; // Tag location

                current[0] -= direction[0]; // retrace cause right not its in the #
                current[1] -= direction[1];

                key += "," + current[0] + "," + current[1]; // Spot where it originally hit it
                // key += "," + lastVisited[0] + "," + lastVisited[1]; // Spot where it originally hit it

                // lastVisited = new int[] {current[0], current[1]};

                if (pounds.get(key) != null) {
                    grid[spot[0]][spot[1]] = previously;
                    return true; // if it goes back to the same one its a loop! since only one outcome it means it must cycle
                }
                pounds.put(key, true);

                direction = nextDirection(direction);
            }
            // grid[current[0]][current[1]] = 'x';
        

            // for (int y = 0; y < grid[0].length; y++) {
            //     for (int x = 0; x < grid.length; x++) {
            //         System.out.print(grid[x][y]);
            //     }
            //     System.out.println();
            // }
            // System.out.println();

            current[0] += direction[0];
            current[1] += direction[1];
        }
        
        grid[spot[0]][spot[1]] = previously;

        return false; // exited out
    }

     public static void main(String []args) throws IOException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day6.txt"));

        String[] rows = puzzleInput.split("\n");

        int[] direction = {0, -1};

        // grid init
        grid = new Character[rows[0].length()][rows.length];
        for (int y = 0; y < rows.length; y++) {
            for (int x = 0; x < rows[y].length(); x++) {
                if (Character.compare(rows[y].charAt(x), '^') == 0) {
                    start = new int[] {x, y};
                    grid[x][y] = '.';
                } else {
                    grid[x][y] = rows[y].charAt(x);
                }
            }
        }

        int foreverLoops = 0;

        int[] current = new int[] {start[0], start[1]};
        HashMap<String, Boolean> visited = new HashMap<>();
        
        // find cycles from original path
        while (inBounds(current)) {
            int[] futureDirection = nextDirection(direction);

            if (Character.compare(grid[current[0]][current[1]], '#') == 0) {
                current[0] -= direction[0]; // retrace cause right not its in the #
                current[1] -= direction[1];


               direction = futureDirection;
            } else {
                // int[] checking = new int[] {current[0], current[1]};

                // while(inBounds(checking)) { // filtering potential
                //     if (Character.compare(grid[checking[0]][checking[1]], '#') == 0) {
                        
                        // int[] nextSpot = new int[] {current[0] + direction[0], current[1] + direction[1]};
                        int[] nextSpot = new int[] {current[0], current[1]};
                        // System.out.printf("%d %d\n", current[0], current[1]);
                        
                        if (inBounds(nextSpot) && Character.compare(grid[nextSpot[0]][nextSpot[1]], '#') != 0 && forever(nextSpot)) {
                            grid[nextSpot[0]][nextSpot[1]] = 'O';
                            visited.put(nextSpot[0] + "," + nextSpot[1], true);
                            foreverLoops++;
                        }
                //         break;
                //     }
                //     checking[0] += futureDirection[0];
                //     checking[1] += futureDirection[1];
                // }
            }

            current[0] += direction[0]; // direction changes can't use variable for both future spots
            current[1] += direction[1];
        }

        
        
        for (int y = 0; y < rows.length; y++) {
            for (int x = 0; x < rows[y].length(); x++) {
                System.out.print(grid[x][y]);
            }
            System.out.println();
        }

        System.out.println(foreverLoops); // a fraud!
        System.out.println(visited.size());
    }
}