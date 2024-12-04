import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day4Part1 {
    public static final String goalWord = "XMAS";  
    
    public static final int[][] directions = {
        {1, 0},
        {1, 1},
        {0, 1},
        {-1, 1},
        {-1, 0},
        {-1, -1},
        {0, -1},
        {1, -1}
    };

    public static int search(Character[][] cells, int x, int y) {
        
        int finds = 0;
        for (int[] direction: directions) {
            int[] currentPoint = {x, y};
            boolean madeIt = true;
            
            for (int i = 0; i < goalWord.length(); i++) {
                if (currentPoint[0] < 0 || currentPoint[0] >= cells.length || currentPoint[1] < 0 || currentPoint[1] >= cells[0].length || cells[currentPoint[0]][currentPoint[1]] != goalWord.charAt(i)) {
                    madeIt = false;
                    break;
                }
                
                currentPoint[0] += direction[0];
                currentPoint[1] += direction[1];
            }
            
            if (madeIt) {
                finds++;
            }
        }
        
        return finds;
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
        for (int x = 0; x < rows.length; x++) {
            for (int y = 0; y < rows[0].length(); y++) {
                finds += search(cells, x, y);
            }
        }
        
        System.out.println(finds);
     }
}

