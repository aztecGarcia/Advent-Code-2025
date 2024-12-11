import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10Part1 {

    public static String getKey(Integer[] point) {
        return point[0] + "," + point[1];
    }
    
    public static boolean inBounds(Integer[][] grid, Integer[] point) {
        return 0 <= point[0] && point[0] < grid.length &&
        0 <= point[1] && point[1] < grid[point[0]].length;
    }
    
    public static void displayGrid(Integer[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void displayGrid(Character[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                System.out.print(grid[y][x] == null ? '.' : grid[y][x]);
            }
            System.out.println();
        }
        System.out.println();
    }

    final static Integer[][] directions = {
        {-1, 0},
        {1, 0},
        {0, 1},
        {0, -1},
    };
    public static int findScore(Integer[][] grid, Integer[] startPoint, Character[][] grid2) {
        Set<String> visited = new HashSet<>();
        Deque<Integer[]> toTravel = new ArrayDeque<>();
        toTravel.add(startPoint);

        int foundEnd = 0;


        while (toTravel.peek() != null) {
            Integer[] point = toTravel.poll();
            
            visited.add(getKey(point));
            int height = grid[point[0]][point[1]];
            
            for (Integer[] direction: directions) {
                Integer[] nextPoint = new Integer[] { point[0] + direction[0], point[1] + direction[1]};
                
                if (inBounds(grid, nextPoint) && (visited.contains(getKey(nextPoint)) == false) &&  grid[nextPoint[0]][nextPoint[1]] == height + 1) {
                    visited.add(getKey(nextPoint));
                    
                    if (grid[nextPoint[0]][nextPoint[1]] != 9) {
                        toTravel.add(nextPoint);
                    } else {
                        foundEnd++;
                    }
                    grid2[nextPoint[0]][nextPoint[1]] = Character.forDigit(grid[nextPoint[0]][nextPoint[1]], 10);
                }
            }
        }

        return foundEnd;
    }

    public static int parseWithDefault(String number, int defaultVal) {
        try {
          return Integer.parseInt(number);
        } catch (NumberFormatException e) {
          return defaultVal;
        }
      }

    public static void main(String []args) throws IOException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day10.txt"));
        
        String[] rows = puzzleInput.split("\n");
        
        Integer[][] grid = new Integer[rows.length][rows[0].length()-1];
        
        Character[][] grid2 = new Character[rows.length][rows[0].length()-1];
        
        List<Integer[]> startingPoints = new ArrayList<>();
        
        int y = 0;
        for (String row: rows) {
            for (int x = 0; x < row.length() - 1; x++) { 
                grid[y][x] = parseWithDefault(row.substring(x, x+1).trim(), -1);
                
                if (Character.compare(row.charAt(x), '0') == 0) {
                    startingPoints.add(new Integer[] {y, x});
                }
            }
            
            y++;
        }

        
        displayGrid(grid);
        int sum = 0;
        
        for (Integer[] point: startingPoints) {
            grid2 = new Character[rows.length][rows[0].length()-1];
            int score = findScore(grid, point, grid2);
            sum += score;
        }
        
        System.out.println(sum);
     }
}
