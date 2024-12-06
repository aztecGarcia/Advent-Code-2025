import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Day6Part1 {
    static Character[][] grid;

    public static boolean inBounds(int[] point) {
        return point[0] >= 0 && point[0] < grid.length && point[1] >= 0 && point[1] < grid[0].length;
    }

     public static void main(String []args) throws IOException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day6.txt"));

        String[] rows = puzzleInput.split("\n");

        int[] direction = {0, -1};
        int[] current = {0, 0};

        HashMap<String, Boolean> visited = new HashMap<>();
        
        System.out.println(rows.length);

        grid = new Character[rows[0].length()][rows.length];
        for (int y = 0; y < rows.length; y++) {
            for (int x = 0; x < rows[y].length(); x++) {
                if (Character.compare(rows[y].charAt(x), '^') == 0) {
                    current = new int[] {x, y};
                    grid[x][y] = '.';
                } else {
                    // System.out.printf("%d %d\n", x, y);
                    grid[x][y] = rows[y].charAt(x);
                }
            }
        }

        while (inBounds(current)) {
            if (Character.compare(grid[current[0]][current[1]], '#') == 0) {
                current[0] -= direction[0]; // retrace
                current[1] -= direction[1];

                int temp = direction[0]; // since up is negative then we actually rotate counter 90
                direction[0] = -direction[1];
                direction[1] = temp;
                System.out.printf("(%d, %d)", current[0], current[1]);
                System.out.printf(" DR(%d, %d)\n", direction[0], direction[1]);
            } else { 
                visited.put(current[0] + "," + current[1], true);
                grid[current[0]][current[1]] = 'X';
                System.out.printf("(%d, %d)", current[0], current[1]);
                System.out.printf(" DR(%d, %d)\n", direction[0], direction[1]);
            }



            current[0] += direction[0];
            current[1] += direction[1];
        }
        
        for (int y = 0; y < rows.length; y++) {
            for (int x = 0; x < rows[y].length(); x++) {
                System.out.print(grid[x][y]);
            }
            System.out.println();
        }

        System.out.println(visited.size());
    }
}


