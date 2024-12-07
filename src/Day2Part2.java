import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day2Part2 {

    public static int checkWithout(Integer[] numList, int withoutIdx) {
      int previous = numList[0];
      Integer previousSlope = null;
      
      int current = 0;

      System.out.println(withoutIdx);
      if (withoutIdx == 0) {
        previous = numList[1];
      }

      for (int i = 1; i < numList.length; i++) {
        if (withoutIdx == 0 && i == 1) { // special ahh case
          i++;
        }

        if (i == withoutIdx) {
          
          continue;
        }

        current = numList[i];

        int slope = previous - current;
        int slopeAbs = Math.abs(slope);


        if ((previousSlope != null && previousSlope * slope <= 0) || 1 > slopeAbs || slopeAbs > 3) {
          return i;
        }

        
        previous = current;
        if (previousSlope == null) previousSlope = slope;
      }

      return -1;
    }
    public static void main(String[] args) throws FileNotFoundException {
      Scanner puzzleScan = new Scanner(new File("PuzzleInput/Day2.txt"));
      int safe = 0;
      int line = 0;

      while (puzzleScan.hasNextLine()) {
        line++;
        String lineStr = puzzleScan.nextLine();
        Scanner lineScan = new Scanner (lineStr);
        System.out.println(lineStr);
        int previous = lineScan.nextInt();

        boolean wasSafe = false;
        
        ArrayList <Integer> numList = new ArrayList<>();
        numList.add(previous);
        
        while (lineScan.hasNextInt()) {
          numList.add(lineScan.nextInt());
        }

        
        Integer[] numArray = numList.toArray(new Integer[numList.size()]);

        for (int i = -1; i < numArray.length ; i++) {
          int failIdx = checkWithout(numArray, i);

          if (failIdx == -1) {
            wasSafe = true;
            break;
          }
        }


        if (wasSafe) { System.out.println(" SAFE**"); safe++;} else { System.out.println(" UNSAFE");};

        lineScan.close();

        // if (line > 5) return;
      }
      
      puzzleScan.close();

      System.out.println(safe);
    }
  }