import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5Part1 {

     public static void main(String []args) throws IOException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day5.txt"));

        String[] splitInput = puzzleInput.split("=");
        Map<String, List<Integer>> before = new HashMap<>();
        Map<String, List<Integer>> after = new HashMap<>();
        
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
        int safe = 0;
        for (String row: rows) {
            String[] cells = row.split(",");
            
            String middle = cells[(cells.length-1)/2];
            boolean madeIt = true;
            for (int x = 0; x < cells.length; x++) {
                String cellValue = cells[x].trim();
                
                for (int checkX = 0; checkX < cells.length; checkX++) {
                    if (checkX == x) continue;
                    int checkValue = Integer.parseInt(cells[checkX].trim());
                    
                    // checking ones behind it
                    if (checkX < x  && (before.get(cellValue) != null && !before.get(cellValue).contains(checkValue) || after.get(cellValue) != null && after.get(cellValue).contains(checkValue))) {
                        madeIt = false;
                        break;
                    }
                    
                    // checking ones infront
                    if (checkX > x  && (after.get(cellValue) != null && !after.get(cellValue).contains(checkValue) || before.get(cellValue) != null && before.get(cellValue).contains(checkValue))) {
                        madeIt = false;
                        break;
                    }
                }
                
                if (madeIt == false) {
                    break;
                }
            }
            
            if (madeIt) {
                safe += Integer.parseInt(middle.trim());
            }
        }
        System.out.println(safe);
     }
}


