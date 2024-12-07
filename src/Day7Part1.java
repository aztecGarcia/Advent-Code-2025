import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day7Part1 {
     public static void main(String []args) throws IOException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day7.txt"));

        long sum = 0;

        for (String row : puzzleInput.split("\n")) {
            String[] cells = row.split(":? ");
            long goal = Long.parseLong(cells[0]);

            int[] permutation = new int[cells.length-1];

            for (int perm = 0; perm < Math.pow(2, cells.length-2); perm++) {
                long total = Long.parseLong(cells[1].trim());
                for (int i = 2; i < cells.length; i++) {
                    long cellValue = Long.parseLong(cells[i].trim());
                    if (permutation[i-2] == 0) {
                        total *= cellValue;
                    } else {
                        total += cellValue;
                    }
                }

                if (total == goal) {
                    sum += goal;
                    break;
                }

                permutation[0]++;
                for (int up = 0; up < permutation.length; up++) {
                    if (permutation[up] == 2) {
                        permutation[up] = 0;
                        permutation[up+1] += 1;
                    }
                }
            }
        }

        System.out.println(sum);
    }
}


