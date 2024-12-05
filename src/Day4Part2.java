import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day4Part2 {
    public static final Character centerLetter = 'A';  
    
    public static final int[][] directions = {
        {1, 1},
        {-1, 1},
        {-1, -1},
        {1, -1}
    };
    public static final String order = "MMSS";

    public static boolean outOfBounds(Character[][] cells, int[] currentPoint) {
        return (currentPoint[0] < 0 || currentPoint[0] >= cells.length || currentPoint[1] < 0 || currentPoint[1] >= cells[currentPoint[0]].length);
    }

    public static int search(Character[][] cells, int x, int y) {
        int[] currentPoint = {x, y};
        char[] xPoints = new char[4];
        
        if (outOfBounds(cells, currentPoint) || cells[currentPoint[0]][currentPoint[1]].compareTo(centerLetter) != 0) {
            return 0;
        }
    
        int dirIdx = 0;
        for (int[] direction: directions) {
            currentPoint[0] = x;
            currentPoint[1] = y;
            // System.out.printf("Found center: %d %d\n", x, y);

            for (int i = 0; i < 1; i++) {
                currentPoint[0] += direction[0];
                currentPoint[1] += direction[1];

                if (outOfBounds(cells, currentPoint)) {
                    return 0;
                } 

                char cellChar = cells[currentPoint[0]][currentPoint[1]];
                
                if (cellChar != 'M' && cellChar != 'S') {
                    return 0;
                }
                
                System.out.printf("Adding this one: %s to %d \n", cells[currentPoint[0]][currentPoint[1]], dirIdx);
                xPoints[dirIdx] = cells[currentPoint[0]][currentPoint[1]];
            }
            
            dirIdx++;
        }
        
        
        String joined = new String(xPoints);
        for (int i = 0; i < joined.length(); i++) {
            String cycled = joined.substring(i) + joined.substring(0, i);
            if (cycled.compareTo(order) == 0) {
                System.out.printf("Found center: %d %d\n", x, y);
                return 1;
            }
        }

        return 0;
    }

     public static void main(String []args) throws IOException {
       String puzzleInput = Files.readString(Path.of("PuzzleInput/Day4.txt"));
        
        String[] rows = puzzleInput.split("\n");
        Character[][] cells = new Character[rows[0].length()][rows.length];
        
        for (int y = 0; y < rows.length; y++) {
          for (int x = 0; x < rows[y].length(); x++) {
              cells[x][y] = rows[y].charAt(x);
          }
        }
        
        int finds = 0;
        for (int y = 0; y < rows.length; y++) {
            for (int x = 0; x < rows[y].length(); x++) {
                finds += search(cells, x, y);
            }
        }
        
        System.out.println(finds);
     }
}

