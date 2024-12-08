import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day8Part2 {

    public static boolean inBounds(Character[][] grid, int[] point) {
        return point[0] >= 0 && point[0] < grid.length && point[1] >= 0 && point[1] < grid[0].length;
    }
    public static String getKey(int[] point) {
        return String.format("%d,%d", point[0], point[1]);
    }

     public static void main(String []args) throws IOException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day8.txt"));

        String[] rows = puzzleInput.split("\n");

        HashMap<Character, List<Integer[]>> frequencies = new HashMap<Character, List<Integer[]>>();
        Set<String> antennaes = new HashSet<>();
        Set<String> antenodes = new HashSet<>();
        
        Character[][] grid = new Character[rows[0].length()-1][rows.length];
        
        for (int y = 0; y < rows.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                grid[x][y] = rows[y].charAt(x);

                if (Character.compare(grid[x][y], '.') != 0) {
                    if (frequencies.get(grid[x][y]) == null) {
                        frequencies.put(grid[x][y], new ArrayList<Integer[]>());
                    }

                    frequencies.get(grid[x][y]).add(new Integer[] { x, y });
                    antennaes.add(getKey(new int[] { x, y }));
                    antenodes.add(getKey(new int[] { x, y }));
                }
            }
        }


        for (HashMap.Entry<Character, List<Integer[]>> symbolSet: frequencies.entrySet()) {
            Character symbol = symbolSet.getKey();

            List<Integer[]> symbolPoints = symbolSet.getValue();
            for (int start = 0; start < symbolPoints.size(); start++) {
                Integer[] startTab = symbolPoints.get(start);

                for (int check = start; check < symbolPoints.size(); check++) {
                    Integer[] checkTab = symbolPoints.get(check);

                    int[] direction = new int[] { startTab[0] - checkTab[0], startTab[1] - checkTab[1] };
                    if (direction[0] == 0 && direction[0] == 0) continue;
                    
                    boolean p0Bounds = true;
                    boolean p1Bounds = true;

                    int mult = 0;
                    while (p1Bounds == true || p0Bounds == true) {
                        mult++;
                        int[] p0 = new int[] { startTab[0] + direction[0]*mult, startTab[1] + direction[1]*mult };
                        int[] p1 = new int[] { checkTab[0] - direction[0]*mult, checkTab[1] - direction[1]*mult };
                        System.out.printf("(%d,%d) (%d,%d) (%d,%d)\n", startTab[0], startTab[1], checkTab[0], checkTab[1], direction[0], direction[1]);
                        p0Bounds = inBounds(grid, p0);
                        p1Bounds = inBounds(grid, p1);

                        if (inBounds(grid, p0) && Character.compare(grid[p0[0]][p0[1]], symbol) != 0) {
                            grid[p0[0]][p0[1]] = '#';
                            antenodes.add(getKey(p0));
                        }
                        if (inBounds(grid, p1) && Character.compare(grid[p1[0]][p1[1]], symbol) != 0) {
                            grid[p1[0]][p1[1]] = '#';
                            antenodes.add(getKey(p1));
                        }
                    }
                }
            }
        }
        for (int y = 0; y < rows.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                System.out.print(grid[x][y]);
            }
            System.out.println();
        }
        System.out.println(antennaes);
        System.out.println(antenodes);
            System.out.println(antenodes.size());
    }
}


