import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Day15Part1 {   
    final  static Map<Character, Integer[]> symbolToDir = new HashMap<>() {{
        put('^', new Integer[] { -1, 0 });
        put('v', new Integer[] { 1, 0 });
        put('<', new Integer[] { 0, -1 });
        put('>', new Integer[] { 0, 1 });
    }};

    public static Boolean inBounds(Character[][] customGrid, Integer[] point) {
        return 0 <= point[0] && point[0] < customGrid.length &&
        0 <= point[1] && point[1] < customGrid[point[0]].length;
    }

    public static void displayGrid(Character[][] grid) {
        for (Character[] row: grid) {
            for (Character individual: row) {
                if (individual == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(individual);
                }
            }
            System.out.println();
        }
            System.out.println();
    }

     public static void main(String []args) throws IOException, InterruptedException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day15.txt"));

        
        String[] gridDirections = puzzleInput.split("\\r?\\n\\r?\\n");
        String[] rows = gridDirections[0].split("\\r?\\n");

        Character[][] grid = new Character[rows.length][rows[0].length()];
        Integer[] currentAntlerPos = new Integer[2];

        
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                grid[y][x] = rows[y].charAt(x);

                if (Character.compare(grid[y][x], '@') == 0) {
                    currentAntlerPos = new Integer[] { y, x };
                }
            }
        }
        
        for (Character nextDirSymbol: (gridDirections[1].replaceAll("[\\r\\n]", "")).toCharArray()) {
            System.out.println(nextDirSymbol);
            Integer[] dir = symbolToDir.get(nextDirSymbol);


            boolean airGap = false;
            Integer[] check = new Integer[] { currentAntlerPos[0] + dir[0], currentAntlerPos[1] + dir[1] };

            int safeHaven = 0;
            while (inBounds(grid, check)) {
                safeHaven++;
                if (Character.compare(grid[check[0]][check[1]], '.') == 0) {
                    airGap = true;
                    break;
                } else if (Character.compare(grid[check[0]][check[1]], '#') == 0) {
                    break;
                }

                check[0] += dir[0];
                check[1] += dir[1];
            }


            if (airGap && Character.compare(grid[currentAntlerPos[0] + dir[0]][currentAntlerPos[1] + dir[1]], '#') != 0) {
                grid[currentAntlerPos[0]][currentAntlerPos[1]] = '.';
                boolean extra = false;
                check = new Integer[] { currentAntlerPos[0] , currentAntlerPos[1] };
                for (int move = 0; move < safeHaven-1; move++) {
                    check[0] += dir[0];
                    check[1] += dir[1];
        
                    if (Character.compare(grid[check[0]][check[1]], 'O') == 0) {
                        if (move == 0) {
                            grid[check[0]][check[1]] = '.';
                        }
                        grid[check[0] + dir[0]][check[1] + dir[1]] = 'O';
                    }
                }

                
                currentAntlerPos[0] += dir[0];
                currentAntlerPos[1] += dir[1];
                grid[currentAntlerPos[0]][currentAntlerPos[1]] = '@';
            }

            // displayGrid(grid);
        }

        int sum = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (Character.compare(grid[y][x], 'O') == 0) {
                    sum += 100*y+x;
                }
            }
        }

        System.out.println(sum);
     }
}


