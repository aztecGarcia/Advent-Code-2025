/* Online Java Compiler and Editor */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day12Part2 {
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

    public static Boolean inBounds(Character[][] customGrid, Integer[] point) {
        return 0 <= point[0] && point[0] < customGrid.length &&
        0 <= point[1] && point[1] < customGrid[point[0]].length;
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
    
    public static List<Integer> getSingleSides(Integer[] point) {
        List<Integer> sides = new ArrayList();
        Character current = grid[point[0]][point[1]];

        int sideIdx = 0;
        for (Integer[] direction: directions) {
            Integer[] nextPoint = { point[0] + direction[0], point[1] + direction[1] };
            
            if (inBounds(nextPoint) == false || Character.compare(grid[nextPoint[0]][nextPoint[1]], current) != 0) {
                sides.add(sideIdx);
            }
            sideIdx++;
        }
        
        return sides;
    }
            

    public static Integer getSides(Integer[] point, List<Integer[]> sidesVisited) {
// check for straight line
// check if same side;

        int conjoinedSides = 0;
        List<Integer> selfSides = getSingleSides(point);
        // System.out.println(selfSides);

        Character remainSame = grid[point[0]][point[1]];

        int newSidesToBeMade = 0;
        for (Integer side: selfSides) {
            boolean madeThisOne = false;

            for (Integer[] visited: sidesVisited) {
                if (visited[0] != point[0] && visited[1] != point[1]) continue; // useless garbage! lines not diaognals

                List<Integer> visitedSides = getSingleSides(visited);

                // Character[][] debugGrid = new Character[grid.length + 2][grid[0].length + 2];
                // for (int y = 0; y < debugGrid.length; y++) {

                //     for (int x = 0; x < debugGrid[0].length; x++) {
                //         if (inBounds(new Integer[] {y-1, x-1}) == true) {
                //             Character[] row = grid[y-1];
                //             if (row[x-1] == remainSame) {
                //                 debugGrid[y][x] = remainSame;
                //             } else {
                //                 debugGrid[y][x] = '.';
                //             }
                //         } else {
                //             debugGrid[y][x] = '.';
                //         }
                //     }
                // }


                for (int i = 0; i < visitedSides.size(); i++) {
                    if (visitedSides.get(i) == side) {
                        madeThisOne = true;
                        // System.out.printf("(%d,%d) same as (%d,%d)\n", point[0], point[1], visited[0], visited[1]);

                        Integer[] travelingPoint = { point[0], point[1] };
                        boolean horizontal = point[0] == visited[0];
                        int loopAmount = horizontal == true ? Math.abs(point[1] - visited[1]) : Math.abs(point[0] - visited[0]);
                        Integer[] dir = new Integer[] { (visited[0] - point[0])/loopAmount, (visited[1] - point[1])/loopAmount };
                        // System.out.printf("DIR: (%d,%d)\n", dir[0], dir[1]);

                        // debugGrid[travelingPoint[0] + 1][travelingPoint[1] + 1] = 'W';
                        // debugGrid[visited[0] + 1][visited[1] + 1] = 'O';
                        // displayGrid(debugGrid);


                        Integer[] perpDir = directions[side]; // basically side direcrtion lol, its basically behaving as a normal
                        // Integer[] perpDir = new Integer[] { Math.abs(dir[1]), Math.abs(dir[0]) };
                        // Integer[] negPerpDir = new Integer[] { -perpDir[0], -perpDir[1] };

                        Integer[] perpPoint = new Integer[] {travelingPoint[0] + perpDir[0], travelingPoint[1] + perpDir[1]};
                        // Integer[] negPerpPoint = new Integer[] {travelingPoint[0] + negPerpDir[0], travelingPoint[1] + negPerpDir[1]};

                        // Character perp = inBounds(perpPoint) == true && Character.compare(grid[perpPoint[0]][perpPoint[1]], remainSame) == 0 ? remainSame : ' '; //;
                        // Character negPerp = inBounds(negPerpPoint) == true ? grid[negPerpPoint[0]][negPerpPoint[1]] : ' '; //;

                        // check straightness
                // debugGrid[travelingPoint[0] + 1][travelingPoint[1] + 1] = '=';
                // if (inBounds(debugGrid, perpPoint)) {
                //     debugGrid[perpPoint[0] + 1][perpPoint[1] + 1] = '+';
                // }
                // displayGrid(debugGrid);
                        for (int j = 0; j < loopAmount; j++) {
                            if (!inBounds(travelingPoint) || Character.compare(remainSame, grid[travelingPoint[0]][travelingPoint[1]]) != 0) {
                                madeThisOne = false;
                                break;
                            }

                            if (inBounds(perpPoint) && Character.compare(remainSame, grid[perpPoint[0]][perpPoint[1]]) == 0) {
                                madeThisOne = false;
                                break;
                            }

                            // if (inBounds(negPerpPoint) && Character.compare(negPerp, grid[negPerpPoint[0]][negPerpPoint[1]]) != 0) {
                            //     System.out.println("F");
                            //     madeThisOne = false;
                            //     break;
                            // }

                            perpPoint[0] += dir[0]; // so it follows!
                            perpPoint[1] += dir[1];
                            // negPerpPoint[0] += negPerpDir[0];
                            // negPerpPoint[1] += negPerpDir[1];
                            travelingPoint[0] += dir[0];
                            travelingPoint[1] += dir[1];

                            // debugGrid[travelingPoint[0] + 1][travelingPoint[1] + 1] = '=';
                            // if (inBounds(debugGrid, new Integer[] { perpPoint[0] + 1, perpPoint[1] + 1 })) {
                            //     debugGrid[perpPoint[0] + 1][perpPoint[1] + 1] = '+';
                            // }
                            // displayGrid(debugGrid);
                        }

                        if (madeThisOne) {
                            conjoinedSides++;
                            // break; // going to the next side if there is one
                        } else {
                        }
                    }
                }

                if (madeThisOne) {
                    break;
                }
            }

            // System.out.println();
            if (!madeThisOne || sidesVisited.size() == 0) {     
                newSidesToBeMade++;
            }
        }

        if (!sidesVisited.contains(point) && newSidesToBeMade > 0) {
            sidesVisited.add(point); // making history!
        }

        if (conjoinedSides == 0) {
            // sidesVisited.add(point); // making history!
        }
        
        return newSidesToBeMade;
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

                    List<Integer[]> allSides = new ArrayList<>();
                    List<Integer[]> outerEdges = new ArrayList<>();

                    // System.out.println();
                while (expanding.peek() != null) {
                    Integer[] point = expanding.poll();
                    size++;
                    sides+=getSides(point, allSides);
                    
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
                
                // sides = allSides.size();
                // displayGrid(checkGrid);
                // System.out.printf("%d * %d %s\n", size, sides, grid[startPoint[0]][startPoint[1]]);
                sum += size * sides;
            }    
        }
        
        
        System.out.println(sum);
     }
}


