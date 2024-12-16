import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15Part2 {   
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

    public static boolean containsPoint(List<Integer[]> doubleStart, Integer[] point) {
        for (Integer[] check: doubleStart) {

            if (check[0] == point[0] && check[1] == point[1]) {
                // System.out.printf("(%d,%d)\n", point[0], point[1]);
                return true;
            }
        }

        return false;
    }

    public static void displayGrid(Character[][] grid, List<Integer[]> doubleStart) {
        int y = 0;

        // for (Integer[] box: doubleStart) {
        //     System.out.printf("C:(%d,%d)\n", box[0], box[1]);
        // }
        String totalString = "";
        
        for (Character[] row: grid) {
            int x = 0;
            for (Character individual: row) {
                if (containsPoint(doubleStart, new Integer[] {y, x})) {
                    // System.out.printf("C:(%d,%d)\n", y, x);
                    totalString += '[';
                } else if (containsPoint(doubleStart, new Integer[] {y, x-1})) {
                    totalString += ']';
                } else {
                    if (individual == null) {
                        totalString += ' ';
                    } else {
                        totalString += individual;
                    }
                }
                x++;
            }
            y++;
            totalString += "\n";
        }
        totalString += "\n";
        System.out.println(totalString);
    }

    public static Integer[] getBoxOrigin(List<Integer[]> doubleStart, Integer[] point) {
        if (containsPoint(doubleStart, point)) {
            return point;
        } else if (containsPoint(doubleStart, new Integer[] { point[0], point[1] - 1 })) {
            return new Integer[] { point[0], point[1] - 1 };
        }

        return null;
    }

    public static List<Integer[]> getChained(List<Integer[]> doubleStart, Integer[] point, Integer[] dir) {
        List<Integer[]> theChained = new ArrayList<>();
        Deque<Integer[]> toCheck = new ArrayDeque<>();

        toCheck.add(point);

        while(toCheck.peek() != null) {
            Integer[] current = toCheck.poll();
            Integer[] origin = getBoxOrigin(doubleStart, current);

            
            if (origin != null) {
                // System.out.printf("(%d,%d)\n", origin[0], origin[1]);
                theChained.add(origin);

                for (int i = 0; i < 2; i++) {
                    Integer[] nextPoint = new Integer[] { origin[0] + dir[0] , origin[1] + dir[1] + i };
                    Integer[] nextOrigin = getBoxOrigin(doubleStart, nextPoint);
                    
                    if (nextOrigin != null && (nextOrigin[0] != origin[0] || nextOrigin[1] != origin[1])) {
                        toCheck.add(nextPoint);
                    }
                }
            }
        }

        return theChained;
    }

     public static void main(String []args) throws IOException, InterruptedException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day15.txt"));

        
        String[] gridDirections = puzzleInput.split("\\r?\\n\\r?\\n");
        String[] rows = gridDirections[0].split("\\r?\\n");

        Character[][] grid = new Character[rows.length][2*rows[0].length()];
        Integer[] currentAntlerPos = new Integer[2];

        List<Integer[]> doubleStart = new ArrayList<>();

        
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (Character.compare( rows[y].charAt(x/2), 'O') == 0) {
                    if (Character.compare(grid[y][x-1], '[') == 0) {
                        grid[y][x] = '.';
                    } else {
                        grid[y][x] = '[';
                        doubleStart.add(new Integer[] { y, x });
                    }
                } else {
                    grid[y][x] = rows[y].charAt(x/2);
                }

                if (Character.compare(grid[y][x], '@') == 0 && currentAntlerPos[0] == null) {
                    currentAntlerPos = new Integer[] { y, x };
                }
            }
        }

        for (Integer[] start: doubleStart) {

            grid[start[0]][start[1]] = '.';
        }

        grid[currentAntlerPos[0]][currentAntlerPos[1] + 1] = '.';
        displayGrid(grid, doubleStart);
        
        for (Character nextDirSymbol: (gridDirections[1].replaceAll("[\\r\\n]", "")).toCharArray()) {
            Integer[] dir = symbolToDir.get(nextDirSymbol);


            Integer[] check = new Integer[] { currentAntlerPos[0] + dir[0], currentAntlerPos[1] + dir[1] };

            List<Integer[]> chained = new ArrayList<>();

            if (inBounds(grid, check)) {
                chained = getChained(doubleStart, check, dir);
            }


            if (Character.compare(grid[currentAntlerPos[0] + dir[0]][currentAntlerPos[1] + dir[1]], '#') != 0) {
                boolean obstruction = false;

                for (Integer[] box: chained) {
                    for (int i = 0; i < 2; i++) {
                        Integer[] nextPoint = new Integer[] { box[0] + dir[0] , box[1] + dir[1] + i };
                        
                        if (Character.compare(grid[nextPoint[0]][nextPoint[1]], '#') == 0) {
                            obstruction = true;
                        }
                    }
                }

                if (!obstruction) {
                    // System.out.println("No Obstruction moving all boxes");
                    grid[currentAntlerPos[0]][currentAntlerPos[1]] = '.';

                    // for (Integer[] box: doubleStart) {
                    //     System.out.printf("S:(%d,%d)\n", box[0], box[1]);
                    // }

                    List<Integer[]> toAdd = new ArrayList<>();

                    for (int chainIdx = 0; chainIdx < chained.size(); chainIdx++) {
                        Integer[] box = chained.get(chainIdx);
                        int doubleIdx = 0;
                        for (doubleIdx = 0; doubleIdx < doubleStart.size(); doubleIdx++) {
                            Integer[] boxStart = doubleStart.get(doubleIdx);

                            if (boxStart[0] == box[0] && boxStart[1] == box[1]) {
                                break;
                            }
                        }

                        if (doubleIdx == doubleStart.size()) continue;
                        
                        Integer[] nextPoint = new Integer[] { box[0] + dir[0] , box[1] + dir[1] };
                        System.out.printf("I:(%d,%d) O:(%d,%d)\n", box[0], box[1], nextPoint[0], nextPoint[1]);
                        doubleStart.remove(doubleIdx);//, nextPoint);
                        toAdd.add(nextPoint);
                    }

                    for (Integer[] addedPoint: toAdd) {
                        doubleStart.add(addedPoint);
                    }
                    // for (Integer[] box: doubleStart) {
                    //     System.out.printf("E:(%d,%d)\n", box[0], box[1]);
                    // }
                    currentAntlerPos[0] += dir[0];
                    currentAntlerPos[1] += dir[1];
                    grid[currentAntlerPos[0]][currentAntlerPos[1]] = '@';
                }
            }

            // System.out.println(nextDirSymbol);
            // displayGrid(grid, doubleStart);
            // TimeUnit.MILLISECONDS.sleep(10);
        }

        int sum = 0;
        for (Integer[] start: doubleStart) {
            sum += 100*start[0]+start[1];
        }

        System.out.println(sum);
     }
}


