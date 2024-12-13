/* Online Java Compiler and Editor */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class Day12Part1 {
    static final Integer[][] directions = {
        { 0, 1 },
        { 0, -1 },
        { 1, 0 },
        { -1, 0 },
    };
    
    static Character[][] grid;
    
    public static String getKey(Integer[] point) {
        return point[0] + "," + point[1];
    }
    
    public static Boolean inBounds(Integer[] point) {
        return 0 <= point[0] && point[0] < grid.length &&
        0 <= point[1] && point[1] < grid[point[0]].length;
    }
    
    public static void displayGrid(Character[][] grid) {
        for (Character[] row: grid) {
            for (Character individual: row) {
                if (individual == null) {
                    System.out.print(".");
                } else {
                    System.out.print(individual);
                }
            }
            System.out.println();
        }
            System.out.println();
    }
    
    public static Integer getSides(Integer[] point) {
        int sides = 4;
        Character current = grid[point[0]][point[1]];
        for (Integer[] direction: directions) {
            Integer[] nextPoint = { point[0] + direction[0], point[1] + direction[1] };
            
            if (inBounds(nextPoint) == true && Character.compare(grid[nextPoint[0]][nextPoint[1]], current) == 0) {
                sides--;
            }
        }
        
        return sides;
    }
        
     public static void main(String []args) throws IOException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day12.txt"));
        
        String[] rows = puzzleInput.split("\n");
        grid = new Character[rows.length][rows[0].length()];
        
        
        
        for (int y = 0; y < rows.length; y++) {
            for (int x = 0; x < rows[y].length(); x++) {
                grid[y][x] = rows[y].charAt(x);
            }
        }
        
        Set<String> visited = new HashSet<String>();
        Deque<Integer[]> expanding = new ArrayDeque<Integer[]>();
        
        int sum = 0;
        
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid.length; x++) {
                
                Character[][] checkGrid = new Character[rows.length][rows[0].length()];
                Integer[] startPoint = new Integer[] { y, x };
                if (visited.contains(getKey(startPoint))) {
                    continue;
                }
                
                expanding.add(startPoint);
                visited.add(getKey(startPoint));
                
                    int size = 0;
                    int sides = 0;
                while (expanding.peek() != null) {
                    Integer[] point = expanding.poll();
                    size++;
                    sides += getSides(point);
                    
                    // if (visited.contains(getKey(point))) {
                        // continue;
                    // }
                    Character current = grid[point[0]][point[1]];
                    
                     checkGrid[point[0]][point[1]] = current;
                        // System.out.printf("C:(%d,%d) %d\n", point[0], point[1], getSides(point));
                        // System.out.println(current);
                    for (Integer[] direction: directions) {
                        Integer[] nextPoint = { point[0] + direction[0], point[1] + direction[1] };
                
                        // System.out.printf("N:(%d,%d)\n", nextPoint[0], nextPoint[1]);
                        // System.out.println(inBounds(nextPoint));
                        if (inBounds(nextPoint) && visited.contains(getKey(nextPoint)) == false && Character.compare(grid[nextPoint[0]][nextPoint[1]], current) == 0) {
                            // System.out.println("Found same");
                            expanding.add(nextPoint);
                            visited.add(getKey(nextPoint));
                        }
                    }
                    
                }
                
                // displayGrid(checkGrid);
                System.out.printf("%d * %d %s\n", size, sides, grid[startPoint[0]][startPoint[1]]);
                sum += size * sides;
            }    
        }
        
        
        System.out.println(sum);
     }
}


