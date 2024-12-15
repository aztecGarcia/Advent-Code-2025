import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

public class Day14Part2 {
    
    final static Pattern pvPattern = Pattern.compile("=(-?\\d+),(-?\\d+)");
        
    public static void displayGrid(Integer[][] grid) {
        for (Integer[] row: grid) {
            for (Integer individual: row) {
                if (individual == null) {
                    System.out.print(" ");
                } else {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
            System.out.println();
    }

    public static int mod(int x, int n) {
        return (x % n) + (x < 0 ? n : 0);
    }

     public static void main(String []args) throws IOException, InterruptedException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day14.txt"));

        
        String[] rows = puzzleInput.split("\\r?\\n");

        Integer[][] grid = new Integer[103][101];
        List<Integer[][]> allPoints = new ArrayList<>();

        int cycleX = 100;
        int cycleY = 100;
        BufferedImage tree = new BufferedImage(grid[0].length*cycleX, grid.length*cycleY, BufferedImage.TYPE_BYTE_BINARY);


        
        for (String row: rows) {
            Matcher match = pvPattern.matcher(row);


            match.find();
            
            Integer[] point = new Integer[] { Integer.parseInt(match.group(2)), Integer.parseInt(match.group(1)) };

            match.find();
            Integer[] velocity = new Integer[] { Integer.parseInt(match.group(2)), Integer.parseInt(match.group(1)) };

            allPoints.add(new Integer[][] { point, velocity });
        }
        
        for (int seconds = 0; seconds < 8051; seconds++) {
            // grid = new Integer[grid.length][grid[0].length];

            int currentCycleX = seconds%cycleX;
            int currentCycleY = (seconds/cycleX);

            for (Integer[][] pointVelocity: allPoints) {
                Integer[] point = pointVelocity[0];
                Integer[] velocity = pointVelocity[1];

                // System.out.printf("(%d,%d) (%d,%d)\n", point[0], point[1], velocity[0], velocity[1]);

                // if (velocity[0] != -3 || velocity[1] != 2) {
                    // continue;
                // } 
    
                // if (grid[point[0]][point[1]] == null) {
                    tree.setRGB(point[1] + grid[0].length*currentCycleX, point[0] + grid.length*currentCycleY, 0xFFFFFFFF);
                    // grid[point[0]][point[1]] = 1;
                // } else {
                    // tree.setRGB(point[1] + grid[0].length*currentCycleX, point[0] + grid.length*currentCycleY, 0);
                    // grid[point[0]][point[1]]++;
                // }
            }

            // displayGrid(grid);

            for (Integer[][] pointVelocity: allPoints) {
        

                Integer[] point = pointVelocity[0];
                Integer[] velocity = pointVelocity[1];
                pointVelocity[0] = new Integer[] { mod(point[0] + velocity[0], grid.length), mod(point[1] + velocity[1], grid[0].length) };
            
                
                if (velocity[0] == -3 && velocity[1] == 2) {
                    // System.out.printf("(%d,%d)(%d,%d)(%d,%d)(%d,%d)\n", point[0], point[1], velocity[0], velocity[1], pointVelocity[0][0], pointVelocity[0][1], grid.length, grid[0].length);
                    // System.out.println(grid.length);
                    // System.out.println((-2)%grid.length);
                } 
            }

            // System.out.println(seconds);
            
            // TimeUnit.MILLISECONDS.sleep(5);
        }

        System.out.println("Dopne!");

        File treeFile = new File("myTree.jpg");

        ImageIO.write(tree, "png", treeFile);
     }
}

