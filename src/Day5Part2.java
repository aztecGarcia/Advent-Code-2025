import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5Part2 {
    static Map<String, List<Integer>> before;
    static Map<String, List<Integer>> after;

    public static int[] findError(String[] cells) {
        for (int x = 0; x < cells.length; x++) {
            String cellValue = cells[x].trim();
            
            for (int checkX = 0; checkX < cells.length; checkX++) {
                if (checkX == x) continue;
                int checkValue = Integer.parseInt(cells[checkX].trim());
                
                // checking ones behind it
                if (checkX < x  && (before.get(cellValue) != null && !before.get(cellValue).contains(checkValue) || after.get(cellValue) != null && after.get(cellValue).contains(checkValue))) {
                    // returnArray = 

                    return new int[] {x, checkX, -1};
                }
                
                // checking ones infront
                if (checkX > x  && (after.get(cellValue) != null && !after.get(cellValue).contains(checkValue) || before.get(cellValue) != null && before.get(cellValue).contains(checkValue))) {
                    return new int[] {x, checkX, 1};
                }
            }
        }

        return new int[] {-1, -1};
    }

     public static void main(String []args) throws IOException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day5.txt"));

        String[] splitInput = puzzleInput.split("=");
        before = new HashMap<>();
        after = new HashMap<>();
        
        Pattern firstInput = Pattern.compile("(\\d+).(\\d+)");
        Matcher m = firstInput.matcher(splitInput[0]);
        
        while (m.find()) {
            String trimmed1 = m.group(1).trim();
            String trimmed2 = m.group(2).trim();

            if (after.get(trimmed1) == null) {
                after.put(trimmed1, new ArrayList<Integer>());
            }
            
            if (before.get(trimmed2) == null) {
                before.put(trimmed2, new ArrayList<Integer>());
            }
            
            after.get(trimmed1).add(Integer.parseInt(trimmed2));
            before.get(trimmed2).add(Integer.parseInt(trimmed1));
            
        }

        String[] rows = splitInput[1].split("\n");
        int unsafe = 0;
        for (String row: rows) {
            String[] cells = row.split(",");
            
            boolean madeIt = false;
            
            int i = 0;
            while (true) {
                int[] errors = findError(cells);
                if (errors[0] == -1) {
                    if (i == 0) madeIt = true;
                    System.out.printf("Fixed in: %d\n", i);
                    break;
                }

                int breakedIdx = errors[0];
                int goal = errors[1];
                String temp = cells[breakedIdx].trim();
                cells[breakedIdx] = cells[goal].trim();
                cells[goal] = temp;

                System.out.println(String.join(",", cells));

                i++;
            }

            String middle = cells[(cells.length-1)/2];
            
            if (!madeIt) {
                unsafe += Integer.parseInt(middle.trim());
            }
        }
        System.out.println(unsafe);
     }
}


