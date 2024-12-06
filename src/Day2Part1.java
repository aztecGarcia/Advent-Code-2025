import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2Part1 {
    public static void main(String[] args) throws FileNotFoundException {
      Scanner puzzleScan = new Scanner(new File("PuzzleInput/Day2.txt"));
      int safe = 0;

      while (puzzleScan.hasNextLine()) {
        Scanner lineScan = new Scanner (puzzleScan.nextLine());

        boolean wasSafe = true;
        int previous = lineScan.nextInt();
        Integer previousSlope = null;
        
        while (lineScan.hasNextInt()) {
          int current = lineScan.nextInt();
          int slope = previous - current;
          int slopeAbs = Math.abs(slope);

          if ((previousSlope != null && previousSlope * slope <= 0) || 1 > slopeAbs || slopeAbs > 3) {
            wasSafe = false;
            break;
          }

          
          previous = current;
          if (previousSlope == null) previousSlope = slope;
        }
        
        if (wasSafe) safe++;

        lineScan.close();
      }
      
      puzzleScan.close();

      System.out.println(safe);
    }
  }